package com.kodlamaio.rentACar.Business.Concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.IndividualInvoiceService;
import com.kodlamaio.rentACar.Business.Requests.IndividualInvoice.CreateIndividualInvoiceRequest;
import com.kodlamaio.rentACar.Business.Requests.IndividualInvoice.StateUpdateIndividualInvoiceRequest;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
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

	private void checkIfRentalExists(int id)
	{
		boolean result = rentalRepository.existsById(id);
		if (result==false) {
			throw new BusinessException("RENTAL NOT EXIST");
		}
	}
	private void checkIfAdditionalServiceExists(int id)
	{
		boolean result = additionalServiceRepository.existsById(id);
		if (result==false) {
			throw new BusinessException("ADDITIONAL SERVİCE NOT EXIST");
		}
	}
	private void checkIfIndividualCustomerExists(int id)
	{
		boolean result = individualCustomerRepository.existsById(id);
		if (result==false) {
			throw new BusinessException("INDIVIDUAL CUSTOMER NOT EXIST");
		}
	}
	private void checkIfIndividualInvoicesExists(int id)
	{
		boolean result = individualInvoiceRepository.existsById(id);
		if (result==false) {
			throw new BusinessException("INDIVIDUAL INVOICE NOT EXIST");
		}
	}
	private double CalculateTotalPrice(int rentalId,int additionalServiceId) 
	{
		double totalPrice;
		Rental rental =rentalRepository.findById(rentalId).get();
		AdditionalService additionalService = additionalServiceRepository.findById(additionalServiceId).get();
		totalPrice=rental.getTotalPrice()+additionalService.getTotalPrice();
		return totalPrice;
	}

}
