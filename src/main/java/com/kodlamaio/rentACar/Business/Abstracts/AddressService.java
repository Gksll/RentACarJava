package com.kodlamaio.rentACar.Business.Abstracts;

import java.util.List;

import com.kodlamaio.rentACar.Business.Requests.address.CreateAddressRequest;
import com.kodlamaio.rentACar.Business.Requests.address.DeleteAddressRequest;
import com.kodlamaio.rentACar.Business.Requests.address.UpdateAddressRequest;
import com.kodlamaio.rentACar.Business.Responces.address.GetAddressResponse;
import com.kodlamaio.rentACar.Business.Responces.address.GetAllAddressResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.Address;


public interface AddressService {
	Result addAddress(CreateAddressRequest createAddressRequest);
	Result delete(DeleteAddressRequest deleteAddressRequest);
	Result update(UpdateAddressRequest updateAddressRequest);
	DataResult<Address> getById(GetAddressResponse getAddressResponse);
	DataResult<List<GetAllAddressResponse>> getAll();
}
