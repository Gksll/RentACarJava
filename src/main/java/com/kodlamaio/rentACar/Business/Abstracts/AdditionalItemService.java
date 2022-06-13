package com.kodlamaio.rentACar.Business.Abstracts;

import java.util.List;

import com.kodlamaio.rentACar.Business.Requests.additionalItem.CreateAdditionalItemRequest;
import com.kodlamaio.rentACar.Business.Requests.additionalItem.DeleteAdditionalItemRequest;
import com.kodlamaio.rentACar.Business.Requests.additionalItem.UpdateAdditionalItemRequest;
import com.kodlamaio.rentACar.Business.Responces.additionalItem.GetAdditionalItemResponse;
import com.kodlamaio.rentACar.Business.Responces.additionalItem.GetAllAdditionalItemResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.AdditionalItem;

public interface AdditionalItemService {
	Result add(CreateAdditionalItemRequest createAdditionalItemRequest);

	Result delete(DeleteAdditionalItemRequest deleteAdditionalItemRequest);

	Result update(UpdateAdditionalItemRequest updateAdditionalItemRequest);

	DataResult<List<GetAllAdditionalItemResponse>> getAll();

	DataResult<AdditionalItem> getById(GetAdditionalItemResponse getAdditionalItemResponse);
}
