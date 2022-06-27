package com.kodlamaio.rentACar.Business.Abstracts;

import java.util.List;

import com.kodlamaio.rentACar.Business.Requests.corporateCustomer.CreateCorporateCustomerRequest;
import com.kodlamaio.rentACar.Business.Requests.corporateCustomer.DeleteCorporateCustomerRequest;
import com.kodlamaio.rentACar.Business.Requests.corporateCustomer.UpdateCorporateCustomerRequest;
import com.kodlamaio.rentACar.Business.Responces.corporateCustomer.GetAllCorporateCustomerResponse;
import com.kodlamaio.rentACar.Business.Responces.corporateCustomer.GetCorporateCustomerResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.CorporateCustomer;


public interface CorporateCustomerService {

	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest);

	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);

	Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest);
	
	DataResult<CorporateCustomer> getById(GetCorporateCustomerResponse getCorporateCustomerResponse);
	
	DataResult<List<GetAllCorporateCustomerResponse>> getAll();

}
