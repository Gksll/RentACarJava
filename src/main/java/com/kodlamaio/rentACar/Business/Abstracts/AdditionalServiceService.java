package com.kodlamaio.rentACar.Business.Abstracts;

import java.util.List;

import com.kodlamaio.rentACar.Business.Requests.additionalService.CreateAdditionalServiceRequest;
import com.kodlamaio.rentACar.Business.Requests.additionalService.DeleteAdditionalServiceRequest;
import com.kodlamaio.rentACar.Business.Requests.additionalService.UpdateAdditionalServiceRequest;
import com.kodlamaio.rentACar.Business.Responces.additionalService.GetAdditionalServiceResponse;
import com.kodlamaio.rentACar.Business.Responces.additionalService.GetAllAdditionalServiceResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.AdditionalService;

public interface AdditionalServiceService {
	Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest);

	Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest);

	Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest);

	DataResult<List<GetAllAdditionalServiceResponse>> getAll();

	DataResult<AdditionalService> getById(GetAdditionalServiceResponse getAdditionalServiceResponse);
}
