package com.kodlamaio.rentACar.Api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.kodlamaio.rentACar.Core.Utilities.Results.ErrorResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Entities.Concretes.Address;

import lombok.var;

@RestController
@RequestMapping("api/address")
public class AddressesController {
	@Autowired
	private AddressService addressService;

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateAddressRequest createAddressRequest) {
		var result = addressService.addAddress(createAddressRequest);
		if (result.isSuccess()) {
			return new SuccessResult();
		}
		return new ErrorResult();
	}

	@PostMapping("/update")
	public Result update(@RequestBody UpdateAddressRequest updatecarRequest) {
		var result = addressService.update(updatecarRequest);
		if (result.isSuccess()) {
			return new SuccessResult("güncelledim");
		}
		return new ErrorResult("hata");
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteAddressRequest deleteCarRequest) {
		addressService.delete(deleteCarRequest);
		return new SuccessResult();
	}

	@GetMapping("/getbyid")
	public DataResult<Address> getById(@RequestBody GetAddressResponse getAddressResponse) {
		return this.addressService.getById(getAddressResponse);
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllAddressResponse>> getAll() {
		return this.addressService.getAll();
	}
}