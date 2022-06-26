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
import com.kodlamaio.rentACar.Entities.Concretes.AdditionalService;

@RestController
@RequestMapping("api/additionalService")
public class AdditionalServicesController {
	private AdditionalServiceService additionalService;

	public AdditionalServicesController(AdditionalServiceService additionalService) {
		this.additionalService = additionalService;
	}

	@PostMapping("/add")
	public Result Add(@RequestBody CreateAdditionalServiceRequest additionalServiceRequest) {
		return additionalService.add(additionalServiceRequest);
	}

	@PostMapping("/update")
	public Result Update(@RequestBody UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		return additionalService.update(updateAdditionalServiceRequest);
	}

	@PostMapping("/delete")
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
		return additionalService.delete(deleteAdditionalServiceRequest);
	}

	@GetMapping("/getById")
	public DataResult<AdditionalService> GetById(GetAdditionalServiceResponse getAdditionalServiceResponse) {
		return additionalService.getById(getAdditionalServiceResponse);
	}

	@GetMapping("/getAll")
	public DataResult<List<GetAllAdditionalServiceResponse>> getAll() {
		return additionalService.getAll();
	}

}
