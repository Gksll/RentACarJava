package com.kodlamaio.rentACar.Business.Concretes;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.IndividualCustomerService;
import com.kodlamaio.rentACar.Business.Requests.individualCustomer.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.Business.Requests.individualCustomer.DeleteIndividualCustomerRequest;
import com.kodlamaio.rentACar.Business.Requests.individualCustomer.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.Business.Responces.individualCustomer.GetAllIndividualCustomerResponse;
import com.kodlamaio.rentACar.Business.Responces.individualCustomer.GetIndividualCustomerResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessDataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.Core.webservices.MernisServiceAdapter;
import com.kodlamaio.rentACar.DataAccess.Abstracts.AddressRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.IndividualCustomerRepository;
import com.kodlamaio.rentACar.Entities.Concretes.Address;
import com.kodlamaio.rentACar.Entities.Concretes.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

	@Autowired
	IndividualCustomerRepository individualCustomerRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ModelMapperService modelMapperService;
	@Autowired
	private MernisServiceAdapter mernisServiceadapter;

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws NumberFormatException, RemoteException {
		IndividualCustomer individualCustomer = modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);
		checkIfRealPerson(individualCustomer);
		CheckCustomerExists(individualCustomer.getIdentityNumber());
		checkAddress(individualCustomer.getAddress().getId());
		individualCustomerRepository.save(individualCustomer);
		return new SuccessResult("added successfully");
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws NumberFormatException, RemoteException {
		CheckCustomerExists(updateIndividualCustomerRequest.getIdentityNumber());
		checkIfCustomerExistsById(updateIndividualCustomerRequest.getId());
		IndividualCustomer individualCustomerToUpdate = modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);
		checkIfRealPerson(individualCustomerToUpdate);
		checkAddress(individualCustomerToUpdate.getAddress().getId());
		individualCustomerRepository.save(individualCustomerToUpdate);
		return new SuccessResult("updated successfully");
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		checkIfCustomerExistsById(deleteIndividualCustomerRequest.getId());
		individualCustomerRepository.deleteById(deleteIndividualCustomerRequest.getId());
		return new SuccessResult("deleted successfully");
	}

	@Override
	public DataResult<IndividualCustomer> getById(GetIndividualCustomerResponse getIndividualCustomerResponse) {
		checkIfCustomerExistsById(getIndividualCustomerResponse.getId());
		IndividualCustomer individualCustomer = modelMapperService.forResponce().map(getIndividualCustomerResponse, IndividualCustomer.class);
		individualCustomer = individualCustomerRepository.findById(getIndividualCustomerResponse.getId()).get();
		return new SuccessDataResult<IndividualCustomer>(individualCustomer, "listed successfully");
	}

	@Override
	public DataResult<List<GetAllIndividualCustomerResponse>> getAll() {
		List<IndividualCustomer> individualCustomers = individualCustomerRepository.findAll();
		List<GetAllIndividualCustomerResponse> responce = individualCustomers.stream()
				.map(item -> modelMapperService.forResponce().map(item, GetAllIndividualCustomerResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllIndividualCustomerResponse>>(responce, "listed successfully");
	}

	@Override
	public DataResult<List<GetAllIndividualCustomerResponse>> getAll(Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<IndividualCustomer> individualCustomers = individualCustomerRepository.findAll(pageable).getContent();
		List<GetAllIndividualCustomerResponse> responce = individualCustomers.stream()
				.map(item -> modelMapperService.forResponce().map(item, GetAllIndividualCustomerResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllIndividualCustomerResponse>>(responce,
				"listed successfully by using pagenitation");
	}

	@Override
	public DataResult<String> GetAddressByCustomerId(GetIndividualCustomerResponse getIndividualCustomerResponse) {
		IndividualCustomer individualCustomer = individualCustomerRepository.findById(getIndividualCustomerResponse.getId()).get();
		CheckCustomerExists(individualCustomer.getIdentityNumber());
		Address adres = individualCustomer.getAddress();
		String data = adres.getAddress();
		return new DataResult<String>(data, true, "listed successfully");
	}

	@Override
	public DataResult<String> GetInvoicesAddressByCustomerId(GetIndividualCustomerResponse getIndividualCustomerResponse) {
		IndividualCustomer individualCustomer = individualCustomerRepository.findById(getIndividualCustomerResponse.getId()).get();
		CheckCustomerExists(individualCustomer.getIdentityNumber());
		Address adres = individualCustomer.getAddress();
		String data = adres.getInvoiceAdress();
		return new DataResult<String>(data, true, "listed successfully");
	}

	// daha önce ekli mi? TC' ye göre kontrol sağlar
	private void CheckCustomerExists(String identityNumber) {
		List<IndividualCustomer> individualCustomers = individualCustomerRepository.findAll();
		for (IndividualCustomer item : individualCustomers) {
			if (item.getIdentityNumber().equals(identityNumber) ) {
				throw new BusinessException("CUSTOMER EXISTS");
			}
		}
		
	}

//mernis servisini çağırır,kimlik doğrulaması yapar
	private void checkIfRealPerson(IndividualCustomer individualCustomer) throws NumberFormatException, RemoteException {
		if (!mernisServiceadapter.CheckIfRealPerson(individualCustomer)) {
			throw new BusinessException("USER VALIDATION ERROR");
		}
	}

	// adress kontrolü
	private void checkAddress(int id) {
		boolean result = addressRepository.existsById(id);
		if (result == false) {
			throw new BusinessException("Address NOT EXISTS");
		}
	}

	// var olmayan customer kontrolü
	private void checkIfCustomerExistsById(int id) {
		boolean result = individualCustomerRepository.existsById(id);
		if (result == false) {
			throw new BusinessException("CUSTOMER NOT EXISTS");
		}
	}
}
