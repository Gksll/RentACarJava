package com.kodlamaio.rentACar.Api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.AdditionalServiceService;
import com.kodlamaio.rentACar.Business.Requests.additionalService.CreateAdditionalServiceRequest;
import com.kodlamaio.rentACar.Business.Requests.additionalService.DeleteAdditionalServiceRequest;
import com.kodlamaio.rentACar.Business.Requests.additionalService.UpdateAdditionalServiceRequest;
import com.kodlamaio.rentACar.Business.Responces.additionalService.GetAdditionalServiceResponse;
import com.kodlamaio.rentACar.Business.Responces.additionalService.GetAllAdditionalServiceResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Entities.Concretes.AdditionalService;

@RestController
@RequestMapping("api/additionalService")
public class AdditionalServicesController {
	private AdditionalServiceService additionalService;

	public AdditionalServicesController(AdditionalServiceService additionalService) {
		this.additionalService = additionalService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateAdditionalServiceRequest additionalServiceRequest) {
		additionalService.add(additionalServiceRequest);
		return new SuccessResult();
	}

	@PostMapping("/update")
	public Result update(@RequestBody UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		additionalService.update(updateAdditionalServiceRequest);
		return new SuccessResult();
	}

	@PostMapping("/delete")
	public Result update(@RequestBody DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
		additionalService.delete(deleteAdditionalServiceRequest);
		return new SuccessResult();
	}

	@GetMapping("/getbyid")
	public DataResult<AdditionalService> getbyid( GetAdditionalServiceResponse getAdditionalServiceResponse) {
		
		return this.additionalService.getById(getAdditionalServiceResponse);
		
	}
	
	@GetMapping("/getall")
	public DataResult<List<GetAllAdditionalServiceResponse>> getall() {

		return this.additionalService.getAll();
	}

}
