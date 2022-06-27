package com.kodlamaio.rentACar.Business.Concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.CorporateCustomerService;
import com.kodlamaio.rentACar.Business.Requests.corporateCustomer.CreateCorporateCustomerRequest;
import com.kodlamaio.rentACar.Business.Requests.corporateCustomer.DeleteCorporateCustomerRequest;
import com.kodlamaio.rentACar.Business.Requests.corporateCustomer.UpdateCorporateCustomerRequest;
import com.kodlamaio.rentACar.Business.Responces.corporateCustomer.GetAllCorporateCustomerResponse;
import com.kodlamaio.rentACar.Business.Responces.corporateCustomer.GetCorporateCustomerResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessDataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.DataAccess.Abstracts.AddressRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.CorporateCustomerRepository;
import com.kodlamaio.rentACar.Entities.Concretes.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

	@Autowired
	private CorporateCustomerRepository corporateCustomerRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ModelMapperService modelMapperService;


	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {
		checkAddress(createCorporateCustomerRequest.getAddressId());
		CorporateCustomer corporateCustomer =modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
		corporateCustomerRepository.save(corporateCustomer);
		return new SuccessResult("added successfully");
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		checkIfCorporateCustomerExistsById(updateCorporateCustomerRequest.getId());
		checkAddress(updateCorporateCustomerRequest.getAddressId());
		CorporateCustomer corporateCustomer =modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);
		corporateCustomerRepository.save(corporateCustomer);
		return new SuccessResult(updateCorporateCustomerRequest.getId()+" updated successfully");
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {
		checkIfCorporateCustomerExistsById(deleteCorporateCustomerRequest.getId());
		CorporateCustomer corporateCustomerToDelete =modelMapperService.forRequest().map(deleteCorporateCustomerRequest, CorporateCustomer.class);
		
		corporateCustomerRepository.deleteById(corporateCustomerToDelete.getId());
		return new SuccessResult("deleted successfully");
	}

	@Override
	public DataResult<CorporateCustomer> getById(GetCorporateCustomerResponse getCorporateCustomerResponse) {
		checkIfCorporateCustomerExistsById(getCorporateCustomerResponse.getId());
		CorporateCustomer corporateCustomer =modelMapperService.forRequest().map(getCorporateCustomerResponse, CorporateCustomer.class);
		corporateCustomer=corporateCustomerRepository.findById(corporateCustomer.getId()).get();
		return new SuccessDataResult<CorporateCustomer>(corporateCustomer,"listed successfully");
	}

	@Override
	public DataResult<List<GetAllCorporateCustomerResponse>> getAll() {
		List<CorporateCustomer> corporateCustomers = corporateCustomerRepository.findAll();
		List<GetAllCorporateCustomerResponse> responce = corporateCustomers.stream()
				.map(item -> modelMapperService.forResponce().map(item, GetAllCorporateCustomerResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllCorporateCustomerResponse>>(responce, "listed successfully");
	}
	

	// adress kontrolü
	private void checkAddress(int id) {
		boolean result = addressRepository.existsById(id);
		if (result == false) {
			throw new BusinessException("Address NOT EXISTS");
		}
	}

	// var olmayan customer kontrolü
	private void checkIfCorporateCustomerExistsById(int id) {
		boolean result = corporateCustomerRepository.existsById(id);
		if (result == false) {
			throw new BusinessException("CUSTOMER NOT EXISTS");
		}
	}
}
