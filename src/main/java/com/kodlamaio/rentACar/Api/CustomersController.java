package com.kodlamaio.rentACar.Api;

import java.util.List;

import javax.validation.Valid;

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
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.Customer;

@RestController
@RequestMapping("api/customers")
public class CustomersController {

	private CustomerService customerService;
	

	public CustomersController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping("/add")
	public Result Add(@RequestBody @Valid CreateCustomerRequest createCustomerRequest) {
		return customerService.add(createCustomerRequest);
	}

	@PostMapping("/delete")
	public Result Delete(DeleteCustomerRequest deleteCustomerRequest) {
		return customerService.delete(deleteCustomerRequest);
	}

	@PostMapping("/update")
	public Result Update(@RequestBody UpdateCustomerRequest updateCustomerRequest) {
		return	customerService.update(updateCustomerRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<GetAllCustomerResponse>> GetAll() {
		return customerService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<Customer> GetById(GetCustomerResponse getCustomerResponse) {
		return customerService.getById(getCustomerResponse);
	}
	
	@GetMapping("/getAddressByCustomerId")
	public DataResult<String> GetAddressByCustomerId(GetCustomerResponse getCustomerResponse) {
		return customerService.GetAddressByCustomerId(getCustomerResponse);
	}
	
	@GetMapping("/getInvoicesAddressByCustomerId")
	public DataResult<String> GetInvoiceAddressByCustomerId(GetCustomerResponse getCustomerResponse) {
		return customerService.GetInvoicesAddressByCustomerId(getCustomerResponse);
	}

	@GetMapping("/getAllByPage")
	public DataResult<List<GetAllCustomerResponse>> GetAll(@RequestParam int pageNo, int pageSize) {
		return customerService.getAll(pageNo, pageSize);
	}
}
