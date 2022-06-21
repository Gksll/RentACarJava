package com.kodlamaio.rentACar.Api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.CustomerService;
import com.kodlamaio.rentACar.Business.Requests.customer.CreateCustomerRequest;
import com.kodlamaio.rentACar.Business.Requests.customer.DeleteCustomerRequest;
import com.kodlamaio.rentACar.Business.Requests.customer.UpdateCustomerRequest;
import com.kodlamaio.rentACar.Business.Responces.customer.GetAllCustomerResponse;
import com.kodlamaio.rentACar.Business.Responces.customer.GetCustomerResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.ErrorResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Entities.Concretes.Customer;

import lombok.var;

@RestController
@RequestMapping("api/customers")
public class CustomersController {
	@Autowired
	private CustomerService customerService;
	

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCustomerRequest createCustomerRequest) {
		var result = customerService.add(createCustomerRequest);
		if (result.isSuccess()) {
			return new SuccessResult(result.getMessage());
		} else {
			return new ErrorResult(result.getMessage());
		}
	}

	@PostMapping("/delete")
	public Result delete(DeleteCustomerRequest deleteCustomerRequest) {
		customerService.delete(deleteCustomerRequest);
		return new SuccessResult();
	}

	@PostMapping("/update")
	public Result update(@RequestBody UpdateCustomerRequest updateCustomerRequest) {
		customerService.update(updateCustomerRequest);
		return new SuccessResult();
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllCustomerResponse>> GetAll() {
		return this.customerService.getAll();
	}

	@GetMapping("/getbyid")
	public DataResult<Customer> GetById(GetCustomerResponse getCustomerResponse) {
		return this.customerService.getById(getCustomerResponse);
	}
	
	@GetMapping("/getaddressbycustomerid")
	public DataResult<String> GetAddressByCustomerId(GetCustomerResponse getCustomerResponse) {
		return this.customerService.GetAddressByCustomerId(getCustomerResponse);
	}
	
	@GetMapping("/getinvoicesaddressbycustomerid")
	public DataResult<String> GetInvoiceAddressByCustomerId(GetCustomerResponse getCustomerResponse) {
		return this.customerService.GetInvoicesAddressByCustomerId(getCustomerResponse);
	}

	@GetMapping("/getallbypage")
	public DataResult<List<GetAllCustomerResponse>> getAll(@RequestParam int pageNo, int pageSize) {
		return this.customerService.getAll(pageNo, pageSize);
	}
}
