package com.kodlamaio.rentACar.Business.Concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.AddressService;
import com.kodlamaio.rentACar.Business.Requests.address.CreateAddressRequest;
import com.kodlamaio.rentACar.Business.Requests.address.DeleteAddressRequest;
import com.kodlamaio.rentACar.Business.Requests.address.UpdateAddressRequest;
import com.kodlamaio.rentACar.Business.Responces.address.GetAddressResponse;
import com.kodlamaio.rentACar.Business.Responces.address.GetAllAddressResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessDataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.DataAccess.Abstracts.AddressRepository;
import com.kodlamaio.rentACar.Entities.Concretes.Address;

@Service
public class AddressManager implements AddressService {
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ModelMapperService mapperService;

	@Override
	public Result addAddress(CreateAddressRequest createAddressRequest) {
		Address address = this.mapperService.forRequest().map(createAddressRequest, Address.class);
		if (address.getInvoiceAdress()==null || address.getInvoiceAdress()=="") {
			address.setInvoiceAdress(address.getAddress());
		}
		addressRepository.save(address);
		return new SuccessResult("added successfully");
	}

	@Override
	public Result delete(DeleteAddressRequest deleteAddressRequest) {
		checkIfAddressExistsById(deleteAddressRequest.getId());
		Address address = this.mapperService.forRequest().map(deleteAddressRequest, Address.class);
		addressRepository.delete(address);
		return new SuccessResult("deleted successfully");
	}

	@Override
	public Result update(UpdateAddressRequest updateAddressRequest) {
		checkIfAddressExistsById(updateAddressRequest.getId());
		Address addressToUpdate = this.mapperService.forRequest().map(updateAddressRequest, Address.class);
		addressRepository.save(addressToUpdate);
		return new SuccessResult("updated successfully");
	}

	@Override
	public DataResult<Address> getById(GetAddressResponse getAddressResponse) {
		checkIfAddressExistsById(getAddressResponse.getId());
		Address addressToGet = this.mapperService.forRequest().map(getAddressResponse, Address.class);
		addressToGet = addressRepository.findById(getAddressResponse.getId()).get();
		return new SuccessDataResult<Address>(addressToGet,"listed successfully");
	}

	@Override
	public DataResult<List<GetAllAddressResponse>> getAll() {
		List<Address> addresses = this.addressRepository.findAll();
		List<GetAllAddressResponse> responce = addresses.stream()
				.map(item -> this.mapperService.forResponce().map(item, GetAllAddressResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllAddressResponse>>(responce,"listed successfully");
	}
	
	private void checkIfAddressExistsById(int id) 
	{
		boolean result = addressRepository.existsById(id);
		if (result==false) {
			throw new BusinessException("ADDRESS NOT EXISTS");
		}
	}
}
