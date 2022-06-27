package com.kodlamaio.rentACar.Business.Abstracts;

import java.rmi.RemoteException;
import java.util.List;

import com.kodlamaio.rentACar.Business.Requests.individualCustomer.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.Business.Requests.individualCustomer.DeleteIndividualCustomerRequest;
import com.kodlamaio.rentACar.Business.Requests.individualCustomer.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.Business.Responces.individualCustomer.GetAllIndividualCustomerResponse;
import com.kodlamaio.rentACar.Business.Responces.individualCustomer.GetIndividualCustomerResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.IndividualCustomer;

public interface IndividualCustomerService {
	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest)throws NumberFormatException, RemoteException ;

	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest)throws NumberFormatException, RemoteException ;

	Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest);

	DataResult<IndividualCustomer> getById(GetIndividualCustomerResponse getIndividualCustomerResponse);

	DataResult<List<GetAllIndividualCustomerResponse>> getAll();
	
	DataResult<List<GetAllIndividualCustomerResponse>> getAll(Integer pageNo,Integer pageSize);
	
	DataResult<String> GetAddressByCustomerId(GetIndividualCustomerResponse getIndividualCustomerResponse);
	
	DataResult<String> GetInvoicesAddressByCustomerId(GetIndividualCustomerResponse getIndividualCustomerResponse);
}
