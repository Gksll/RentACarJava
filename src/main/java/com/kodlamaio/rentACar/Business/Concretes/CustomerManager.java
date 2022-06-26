package com.kodlamaio.rentACar.Business.Concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.CustomerService;
import com.kodlamaio.rentACar.Business.Requests.customer.CreateCustomerRequest;
import com.kodlamaio.rentACar.Business.Requests.customer.DeleteCustomerRequest;
import com.kodlamaio.rentACar.Business.Requests.customer.UpdateCustomerRequest;
import com.kodlamaio.rentACar.Business.Responces.customer.GetAllCustomerResponse;
import com.kodlamaio.rentACar.Business.Responces.customer.GetCustomerResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.ErrorResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessDataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.Core.webservices.MernisServiceAdapter;
import com.kodlamaio.rentACar.DataAccess.Abstracts.CustomerRepository;
import com.kodlamaio.rentACar.Entities.Concretes.Address;
import com.kodlamaio.rentACar.Entities.Concretes.Customer;

@Service
public class CustomerManager implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	private ModelMapperService modelMapperService;

	private MernisServiceAdapter adapter;

	public CustomerManager(MernisServiceAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public Result add(CreateCustomerRequest createCustomerRequest) {
		Customer customer = modelMapperService.forRequest().map(createCustomerRequest, Customer.class);
		
		if (CheckIfRealPerson(customer).isSuccess() && CheckCustomerExists(customer).isSuccess()) { 
			this.customerRepository.save(customer);
			return new SuccessResult();
		} else {
			
			return new ErrorResult("Müşteri gerçek değil yada daha önce eklenmiş");
		}
	}

	@Override
	public Result update(UpdateCustomerRequest updateCustomerRequest) {
		Customer userToUpdate = modelMapperService.forRequest().map(updateCustomerRequest, Customer.class);
		customerRepository.save(userToUpdate);
		return new SuccessResult();
	}

	@Override
	public Result delete(DeleteCustomerRequest deleteCustomerRequest) {
		customerRepository.deleteById(deleteCustomerRequest.getId());
		return new SuccessResult();
	}

	@Override
	public DataResult<Customer> getById(GetCustomerResponse getCustomerResponse) {
		Customer customer = modelMapperService.forResponce().map(getCustomerResponse, Customer.class);
		customer = customerRepository.findById(getCustomerResponse.getId()).get();
		return new SuccessDataResult<Customer>(customer);
	}

	@Override
	public DataResult<List<GetAllCustomerResponse>> getAll() {
		List<Customer> customers = this.customerRepository.findAll();
		List<GetAllCustomerResponse> responce = customers.stream()
				.map(item -> modelMapperService.forResponce().map(item, GetAllCustomerResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllCustomerResponse>>(responce);
	}
	
	@Override
	public DataResult<List<GetAllCustomerResponse>> getAll(Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Customer> customers = this.customerRepository.findAll(pageable).getContent();
		List<GetAllCustomerResponse> responce = customers.stream()
				.map(item -> modelMapperService.forResponce().map(item, GetAllCustomerResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllCustomerResponse>>(responce);
	}

	
	public Result CheckIfRealPerson(Customer customer) {
		return new Result(adapter.CheckIfRealPerson(customer));
	}

	
	public Result CheckCustomerExists(Customer customer) {
		boolean state=false;
		List<Customer> customers = customerRepository.findAll();
		for (Customer item : customers) {
			if (item.getTcNo()==customer.getTcNo()) {
				state=true;
			}
		}
		return new Result(state);
	}

	@Override
	public DataResult<String> GetAddressByCustomerId(GetCustomerResponse getCustomerResponse) {
		Customer customer = customerRepository.findById(getCustomerResponse.getId()).get();
		Address adres =customer.getAddress();
		String data = adres.getAddress();
		return new DataResult<String>(data, true);
	}

	@Override
	public DataResult<String> GetInvoicesAddressByCustomerId(GetCustomerResponse getCustomerResponse) {
		Customer customer = customerRepository.findById(getCustomerResponse.getId()).get();
		Address adres =customer.getAddress();
		String data = adres.getInvoiceAdress();
		return new DataResult<String>(data, true);
	}
	
	

}
