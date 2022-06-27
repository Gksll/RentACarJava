package com.kodlamaio.rentACar.Api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.CorporateCustomerService;
import com.kodlamaio.rentACar.Business.Concretes.CorporateCustomerManager;
import com.kodlamaio.rentACar.Business.Requests.corporateCustomer.CreateCorporateCustomerRequest;
import com.kodlamaio.rentACar.Business.Requests.corporateCustomer.DeleteCorporateCustomerRequest;
import com.kodlamaio.rentACar.Business.Requests.corporateCustomer.UpdateCorporateCustomerRequest;
import com.kodlamaio.rentACar.Business.Responces.corporateCustomer.GetAllCorporateCustomerResponse;
import com.kodlamaio.rentACar.Business.Responces.corporateCustomer.GetCorporateCustomerResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.CorporateCustomer;

@RestController
@RequestMapping("api/corporateCustomers")
public class CorporateCustomersController {

	private CorporateCustomerService corporateCustomerService;
	

	public CorporateCustomersController(CorporateCustomerManager corporateCustomerManager) {
		this.corporateCustomerService = corporateCustomerManager;
	}

	@PostMapping("/add")
	public Result Add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest){
		return corporateCustomerService.add(createCorporateCustomerRequest);
	}

	@PostMapping("/delete")
	public Result Delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {
		return corporateCustomerService.delete(deleteCorporateCustomerRequest);
	}

	@PostMapping("/update")
	public Result Update(@RequestBody UpdateCorporateCustomerRequest updateCorporateCustomerRequest){
		return corporateCustomerService.update(updateCorporateCustomerRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<GetAllCorporateCustomerResponse>> GetAll() {
		return corporateCustomerService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<CorporateCustomer> GetById(GetCorporateCustomerResponse getCorporateCustomerResponse) {
		return corporateCustomerService.getById(getCorporateCustomerResponse);
	}
}
