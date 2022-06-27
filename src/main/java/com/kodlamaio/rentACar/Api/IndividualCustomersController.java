package com.kodlamaio.rentACar.Api;

import java.rmi.RemoteException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.IndividualCustomerService;
import com.kodlamaio.rentACar.Business.Concretes.IndividualCustomerManager;
import com.kodlamaio.rentACar.Business.Requests.individualCustomer.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.Business.Requests.individualCustomer.DeleteIndividualCustomerRequest;
import com.kodlamaio.rentACar.Business.Requests.individualCustomer.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.Business.Responces.individualCustomer.GetAllIndividualCustomerResponse;
import com.kodlamaio.rentACar.Business.Responces.individualCustomer.GetIndividualCustomerResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.IndividualCustomer;

@RestController
@RequestMapping("api/individualCustomers")
public class IndividualCustomersController {

	private IndividualCustomerService individualCustomerService;
	

	public IndividualCustomersController(IndividualCustomerManager individualCustomerManager) {
		this.individualCustomerService = individualCustomerManager;
	}

	@PostMapping("/add")
	public Result Add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest) throws NumberFormatException, RemoteException {
		return individualCustomerService.add(createIndividualCustomerRequest);
	}

	@PostMapping("/delete")
	public Result Delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		return individualCustomerService.delete(deleteIndividualCustomerRequest);
	}

	@PostMapping("/update")
	public Result Update(@RequestBody UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws NumberFormatException, RemoteException {
		return	individualCustomerService.update(updateIndividualCustomerRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<GetAllIndividualCustomerResponse>> GetAll() {
		return individualCustomerService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<IndividualCustomer> GetById(GetIndividualCustomerResponse getIndividualCustomerResponse) {
		return individualCustomerService.getById(getIndividualCustomerResponse);
	}
	
	@GetMapping("/getAddressByCustomerId")
	public DataResult<String> GetAddressByCustomerId(GetIndividualCustomerResponse getIndividualCustomerResponse) {
		return individualCustomerService.GetAddressByCustomerId(getIndividualCustomerResponse);
	}
	
	@GetMapping("/getInvoicesAddressByCustomerId")
	public DataResult<String> GetInvoiceAddressByCustomerId(GetIndividualCustomerResponse getIndividualCustomerResponse) {
		return individualCustomerService.GetInvoicesAddressByCustomerId(getIndividualCustomerResponse);
	}

	@GetMapping("/getAllByPage")
	public DataResult<List<GetAllIndividualCustomerResponse>> GetAll(@RequestParam int pageNo, int pageSize) {
		return individualCustomerService.getAll(pageNo, pageSize);
	}
}
