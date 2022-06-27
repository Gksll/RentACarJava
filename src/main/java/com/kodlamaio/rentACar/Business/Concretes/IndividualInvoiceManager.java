package com.kodlamaio.rentACar.Business.Concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.IndividualInvoiceService;
import com.kodlamaio.rentACar.Business.Requests.IndividualInvoice.CreateIndividualInvoiceRequest;
import com.kodlamaio.rentACar.Business.Requests.IndividualInvoice.StateUpdateIndividualInvoiceRequest;
import com.kodlamaio.rentACar.Business.Responces.IndividualInvoice.GetAllIndividualInvoiceResponse;
import com.kodlamaio.rentACar.Business.Responces.IndividualInvoice.GetIndividualInvoiceResponce;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessDataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.DataAccess.Abstracts.AdditionalServiceRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.IndividualCustomerRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.IndividualInvoiceRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.RentalRepository;
import com.kodlamaio.rentACar.Entities.Concretes.AdditionalService;
import com.kodlamaio.rentACar.Entities.Concretes.IndividualInvoice;
import com.kodlamaio.rentACar.Entities.Concretes.Rental;

@Service
public class IndividualInvoiceManager implements IndividualInvoiceService {
	@Autowired
	private IndividualInvoiceRepository individualInvoiceRepository;
	@Autowired
	private IndividualCustomerRepository individualCustomerRepository;
	@Autowired
	private AdditionalServiceRepository additionalServiceRepository;
	@Autowired
	private RentalRepository rentalRepository;
	@Autowired
	private ModelMapperService modelMapperService;
	@Override
	public Result add(CreateIndividualInvoiceRequest createIndividualInvoiceRequest) {
		checkIfRentalExists(createIndividualInvoiceRequest.getRentalId());
		checkIfAdditionalServiceExists(createIndividualInvoiceRequest.getAdditionalServiceId());
		checkIfIndividualCustomerExists(createIndividualInvoiceRequest.getIndividualCustomerId());
		IndividualInvoice individualInvoice = modelMapperService.forRequest().map(createIndividualInvoiceRequest, IndividualInvoice.class);
		individualInvoice.setState(true);
		individualInvoice.setTotalPrice(CalculateTotalPrice(createIndividualInvoiceRequest.getRentalId(),createIndividualInvoiceRequest.getAdditionalServiceId()));
		individualInvoiceRepository.save(individualInvoice);
		return new SuccessResult("added successfully");
	}
	@Override
	public Result cancelInvoice(StateUpdateIndividualInvoiceRequest stateUpdateIndividualInvoiceRequest) {
		checkIfIndividualInvoicesExists(stateUpdateIndividualInvoiceRequest.getId());
		IndividualInvoice individualInvoiceToCancel = modelMapperService.forRequest().map(stateUpdateIndividualInvoiceRequest, IndividualInvoice.class);
		individualInvoiceToCancel=individualInvoiceRepository.findById(stateUpdateIndividualInvoiceRequest.getId()).get();
		individualInvoiceToCancel.setState(false);
		individualInvoiceRepository.save(individualInvoiceToCancel);
		return new SuccessResult("Invoices state changed to false");
	}
	
	@Override
	public Result activateInvoice(StateUpdateIndividualInvoiceRequest stateUpdateIndividualInvoiceRequest) {
		checkIfIndividualInvoicesExists(stateUpdateIndividualInvoiceRequest.getId());
		IndividualInvoice individualInvoiceToActive = modelMapperService.forRequest().map(stateUpdateIndividualInvoiceRequest, IndividualInvoice.class);
		individualInvoiceToActive=individualInvoiceRepository.findById(stateUpdateIndividualInvoiceRequest.getId()).get();
		individualInvoiceToActive.setState(true);
		individualInvoiceRepository.save(individualInvoiceToActive);
		return new SuccessResult("Invoices state changed to true");
	}
	@Override
	public DataResult<List<GetAllIndividualInvoiceResponse>> getAll() {
		List<IndividualInvoice> individualInvoices = this.individualInvoiceRepository.findAll();
		List<GetAllIndividualInvoiceResponse> responce = individualInvoices.stream()
				.map(item -> modelMapperService.forResponce().map(item, GetAllIndividualInvoiceResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllIndividualInvoiceResponse>>(responce, "the invoices successfully listed");
	}
	@Override
	public DataResult<IndividualInvoice> getById(GetIndividualInvoiceResponce getIndividualInvoiceResponce) {
		checkIfIndividualInvoicesExists(getIndividualInvoiceResponce.getId());
		IndividualInvoice individualInvoiceToGet = modelMapperService.forResponce().map(getIndividualInvoiceResponce, IndividualInvoice.class);
		individualInvoiceToGet = this.individualInvoiceRepository.findById(getIndividualInvoiceResponce.getId()).get();
		return new SuccessDataResult<IndividualInvoice>(individualInvoiceToGet, "the invoices successfully listed");
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
		boolean result = individualCustomerRepository.existsById(id);
		if (result==false) {
			throw new BusinessException("INDIVIDUAL CUSTOMER NOT EXIST");
		}
	}
	//iptal etme ve active etme işlemleri için öncesinde kontrol sağlıyoruz
	private void checkIfIndividualInvoicesExists(int id)
	{
		boolean result = individualInvoiceRepository.existsById(id);
		if (result==false) {
			throw new BusinessException("INDIVIDUAL INVOICE NOT EXIST");
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
