package com.kodlamaio.rentACar.Business.Abstracts;

import java.util.List;

import com.kodlamaio.rentACar.Business.Requests.customer.CreateCustomerRequest;
import com.kodlamaio.rentACar.Business.Requests.customer.DeleteCustomerRequest;
import com.kodlamaio.rentACar.Business.Requests.customer.UpdateCustomerRequest;
import com.kodlamaio.rentACar.Business.Responces.customer.GetAllCustomerResponse;
import com.kodlamaio.rentACar.Business.Responces.customer.GetCustomerResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.Customer;

public interface CustomerService {
	Result add(CreateCustomerRequest createCustomerRequest);

	Result update(UpdateCustomerRequest updateCustomerRequest);

	Result delete(DeleteCustomerRequest deleteCustomerRequest);

	DataResult<Customer> getById(GetCustomerResponse getCustomerResponse);

	DataResult<List<GetAllCustomerResponse>> getAll();
	
	DataResult<List<GetAllCustomerResponse>> getAll(Integer pageNo,Integer pageSize);
	
	Result CheckIfRealPerson(Customer customer);
	Result CheckCustomerExists(Customer customer);
	
	DataResult<String> GetAddressByCustomerId(GetCustomerResponse getCustomerResponse);
	
	DataResult<String> GetInvoicesAddressByCustomerId(GetCustomerResponse getCustomerResponse);
}
