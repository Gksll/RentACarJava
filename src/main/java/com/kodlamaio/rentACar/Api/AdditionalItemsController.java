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
import com.kodlamaio.rentACar.Entities.Concretes.AdditionalItem;

@RestController
@RequestMapping("api/additionalItem")
public class AdditionalItemsController {
	
	private AdditionalItemService additionalService;	

	public AdditionalItemsController(AdditionalItemService additionalService) {
		this.additionalService = additionalService;
	}

	@PostMapping("/add")
	public Result Add(@RequestBody CreateAdditionalItemRequest createAdditionalItemRequest) {
		return additionalService.add(createAdditionalItemRequest);
	}

	@PostMapping("/update")
	public Result Update(@RequestBody UpdateAdditionalItemRequest updateAdditionalItemRequest) {
		return additionalService.update(updateAdditionalItemRequest);
	}

	@PostMapping("/delete")
	public Result Delete( DeleteAdditionalItemRequest deleteAdditionalItemRequest) {
		return additionalService.delete(deleteAdditionalItemRequest);
	}

	@GetMapping("/getById")
	public DataResult<AdditionalItem> GetById( GetAdditionalItemResponse getAdditionalItemResponse) {

		return additionalService.getById(getAdditionalItemResponse);
	}

	@GetMapping("/getAll")
	public DataResult<List<GetAllAdditionalItemResponse>> getAll() {
		return additionalService.getAll();
	}

}
