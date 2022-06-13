package com.kodlamaio.rentACar.Api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.AdditionalItemService;
import com.kodlamaio.rentACar.Business.Requests.additionalItem.CreateAdditionalItemRequest;
import com.kodlamaio.rentACar.Business.Requests.additionalItem.DeleteAdditionalItemRequest;
import com.kodlamaio.rentACar.Business.Requests.additionalItem.UpdateAdditionalItemRequest;
import com.kodlamaio.rentACar.Business.Responces.additionalItem.GetAdditionalItemResponse;
import com.kodlamaio.rentACar.Business.Responces.additionalItem.GetAllAdditionalItemResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Entities.Concretes.AdditionalItem;

@RestController
@RequestMapping("api/additionalItem")
public class AdditionalItemsController {
	private AdditionalItemService additionalService;

	public AdditionalItemsController(AdditionalItemService additionalService) {
		this.additionalService = additionalService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateAdditionalItemRequest createAdditionalItemRequest) {
		additionalService.add(createAdditionalItemRequest);
		return new SuccessResult();
	}

	@PostMapping("/update")
	public Result update(@RequestBody UpdateAdditionalItemRequest updateAdditionalItemRequest) {
		additionalService.update(updateAdditionalItemRequest);
		return new SuccessResult();
	}

	@PostMapping("/delete")
	public Result update(@RequestBody DeleteAdditionalItemRequest deleteAdditionalItemRequest) {
		additionalService.delete(deleteAdditionalItemRequest);
		return new SuccessResult();
	}

	@GetMapping("/getbyid")
	public DataResult<AdditionalItem> getbyid(@RequestBody GetAdditionalItemResponse getAdditionalItemResponse) {

		return this.additionalService.getById(getAdditionalItemResponse);
	}
	
	@GetMapping("/getall")
	public DataResult<List<GetAllAdditionalItemResponse>> getall() {

		return this.additionalService.getAll();
	}

}
