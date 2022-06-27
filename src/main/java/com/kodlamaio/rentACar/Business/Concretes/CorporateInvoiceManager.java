package com.kodlamaio.rentACar.Business.Concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.CorporateInvoiceService;
import com.kodlamaio.rentACar.Business.Requests.corporateInvoice.CreateCorporateInvoiceRequest;
import com.kodlamaio.rentACar.Business.Requests.corporateInvoice.StateUpdateCorporateInvoiceRequest;
import com.kodlamaio.rentACar.Business.Responces.corporateInvoice.GetAllCorporateInvoiceResponse;
import com.kodlamaio.rentACar.Business.Responces.corporateInvoice.GetCorporateInvoiceResponce;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessDataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.DataAccess.Abstracts.AdditionalServiceRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.CorporateCustomerRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.CorporateInvoiceRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.RentalRepository;
import com.kodlamaio.rentACar.Entities.Concretes.AdditionalService;
import com.kodlamaio.rentACar.Entities.Concretes.CorporateInvoice;
import com.kodlamaio.rentACar.Entities.Concretes.Rental;

@Service
public class CorporateInvoiceManager implements CorporateInvoiceService {
	@Autowired
	private CorporateInvoiceRepository corporateInvoiceRepository;
	
	@Autowired
	private CorporateCustomerRepository corporateCustomerRepository;
	
	@Autowired
	private AdditionalServiceRepository additionalServiceRepository;
	@Autowired
	private RentalRepository rentalRepository;
	@Autowired
	private ModelMapperService modelMapperService;
	
	@Override
	public Result add(CreateCorporateInvoiceRequest createCorporateInvoiceRequest) {
		checkIfRentalExists(createCorporateInvoiceRequest.getRentalId());
		checkIfAdditionalServiceExists(createCorporateInvoiceRequest.getAdditionalServiceId());
		checkIfIndividualCustomerExists(createCorporateInvoiceRequest.getCorporateCustomerId());
		CorporateInvoice corporateInvoiceToAdd = modelMapperService.forRequest().map(createCorporateInvoiceRequest, CorporateInvoice.class);
		corporateInvoiceToAdd.setState(true);
		corporateInvoiceToAdd.setTotalPrice(CalculateTotalPrice(createCorporateInvoiceRequest.getRentalId(),createCorporateInvoiceRequest.getAdditionalServiceId()));
		corporateInvoiceRepository.save(corporateInvoiceToAdd);
		return new SuccessResult("added successfully");
	}
	@Override
	public Result cancelInvoice(StateUpdateCorporateInvoiceRequest stateUpdateCorporateInvoiceRequest) {
		checkIfIndividualInvoicesExists(stateUpdateCorporateInvoiceRequest.getId());
		CorporateInvoice corporateInvoiceInvoiceToCancel = modelMapperService.forRequest().map(stateUpdateCorporateInvoiceRequest, CorporateInvoice.class);
		corporateInvoiceInvoiceToCancel=corporateInvoiceRepository.findById(stateUpdateCorporateInvoiceRequest.getId()).get();
		corporateInvoiceInvoiceToCancel.setState(false);
		corporateInvoiceRepository.save(corporateInvoiceInvoiceToCancel);
		return new SuccessResult("Invoices state changed to false");
	}
	
	@Override
	public Result activateInvoice(StateUpdateCorporateInvoiceRequest stateUpdateCorporateInvoiceRequest) {
		checkIfIndividualInvoicesExists(stateUpdateCorporateInvoiceRequest.getId());
		CorporateInvoice corporateInvoiceInvoiceToActive = modelMapperService.forRequest().map(stateUpdateCorporateInvoiceRequest, CorporateInvoice.class);
		corporateInvoiceInvoiceToActive=corporateInvoiceRepository.findById(stateUpdateCorporateInvoiceRequest.getId()).get();
		corporateInvoiceInvoiceToActive.setState(true);
		corporateInvoiceRepository.save(corporateInvoiceInvoiceToActive);
		return new SuccessResult("Invoices state changed to true");
	}
	@Override
	public DataResult<List<GetAllCorporateInvoiceResponse>> getAll() {
		List<CorporateInvoice> corporateInvoices = corporateInvoiceRepository.findAll();
		List<GetAllCorporateInvoiceResponse> responce = corporateInvoices.stream()
				.map(item -> modelMapperService.forResponce().map(item, GetAllCorporateInvoiceResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllCorporateInvoiceResponse>>(responce, "the invoices successfully listed");
	}
	@Override
	public DataResult<CorporateInvoice> getById(GetCorporateInvoiceResponce getCorporateInvoiceResponce) {
		checkIfIndividualInvoicesExists(getCorporateInvoiceResponce.getId());
		CorporateInvoice corporateInvoiceToGet = modelMapperService.forResponce().map(getCorporateInvoiceResponce, CorporateInvoice.class);
		corporateInvoiceToGet = corporateInvoiceRepository.findById(getCorporateInvoiceResponce.getId()).get();
		return new SuccessDataResult<CorporateInvoice>(corporateInvoiceToGet, "the invoices successfully listed");
	}
////faturaya kiralama eklemeden önce kontrol sağlıyoruz
	private void checkIfRentalExists(int id)
	{
		boolean result = rentalRepository.existsById(id);
		if (result==false) {
			throw new BusinessException("RENTAL NOT EXIST");
		}
	}
	//faturaya ek servis eklemeden önce kontrol sağlıyoruz
	private void checkIfAdditionalServiceExists(int id)
	{
		boolean result = additionalServiceRepository.existsById(id);
		if (result==false) {
			throw new BusinessException("ADDITIONAL SERVİCE NOT EXIST");
		}
	}
	//faturaya müşteri eklemeden önce kontrol sağlıyoruz
	private void checkIfIndividualCustomerExists(int id)
	{
		boolean result = corporateCustomerRepository.existsById(id);
		if (result==false) {
			throw new BusinessException("CORPORATE CUSTOMER NOT EXIST");
		}
	}
	//iptal etme ve active etme işlemleri için öncesinde kontrol sağlıyoruz
	private void checkIfIndividualInvoicesExists(int id)
	{
		boolean result = corporateInvoiceRepository.existsById(id);
		if (result==false) {
			throw new BusinessException("CORPORATE INVOICE NOT EXIST");
		}
	}
	//Fatura toplam ücreti hesaplıyoruz
	
	private double CalculateTotalPrice(int rentalId,int additionalServiceId) 
	{
		double totalPrice;
		Rental rental =rentalRepository.findById(rentalId).get();
		AdditionalService additionalService = additionalServiceRepository.findById(additionalServiceId).get();
		totalPrice=rental.getTotalPrice()+additionalService.getTotalPrice();
		return totalPrice;
	}
	

	

}
