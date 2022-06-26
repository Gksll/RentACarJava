package com.kodlamaio.rentACar.Api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.AddressService;
import com.kodlamaio.rentACar.Business.Requests.address.CreateAddressRequest;
import com.kodlamaio.rentACar.Business.Requests.address.DeleteAddressRequest;
import com.kodlamaio.rentACar.Business.Requests.address.UpdateAddressRequest;
import com.kodlamaio.rentACar.Business.Responces.address.GetAddressResponse;
import com.kodlamaio.rentACar.Business.Responces.address.GetAllAddressResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.Address;

@RestController
@RequestMapping("api/address")
public class AddressesController {

	private AddressService addressService;

	public AddressesController(AddressService addressService) {
		this.addressService = addressService;
	}

	@PostMapping("/add")
	public Result Add(@RequestBody @Valid CreateAddressRequest createAddressRequest) {
		return addressService.addAddress(createAddressRequest);
	}

	@PostMapping("/update")
	public Result Update(@RequestBody UpdateAddressRequest updatecarRequest) {
		return addressService.update(updatecarRequest);
	}

	@PostMapping("/delete")
	public Result Delete(DeleteAddressRequest deleteCarRequest) {
		return addressService.delete(deleteCarRequest);
	}

	@GetMapping("/getById")
	public DataResult<Address> GetById(GetAddressResponse getAddressResponse) {
		return addressService.getById(getAddressResponse);
	}

	@GetMapping("/getAll")
	public DataResult<List<GetAllAddressResponse>> getAll() {
		return addressService.getAll();
	}
}