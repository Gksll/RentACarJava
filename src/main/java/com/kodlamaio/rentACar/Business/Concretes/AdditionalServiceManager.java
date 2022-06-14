package com.kodlamaio.rentACar.Business.Concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.kodlamaio.rentACar.Business.Abstracts.AdditionalServiceService;
import com.kodlamaio.rentACar.Business.Requests.additionalService.CreateAdditionalServiceRequest;
import com.kodlamaio.rentACar.Business.Requests.additionalService.DeleteAdditionalServiceRequest;
import com.kodlamaio.rentACar.Business.Requests.additionalService.UpdateAdditionalServiceRequest;
import com.kodlamaio.rentACar.Business.Responces.additionalService.GetAdditionalServiceResponse;
import com.kodlamaio.rentACar.Business.Responces.additionalService.GetAllAdditionalServiceResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessDataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.DataAccess.Abstracts.AdditionalItemRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.AdditionalServiceRepository;
import com.kodlamaio.rentACar.Entities.Concretes.AdditionalItem;
import com.kodlamaio.rentACar.Entities.Concretes.AdditionalService;


@Service
public class AdditionalServiceManager implements AdditionalServiceService {
	@Autowired
	private AdditionalServiceRepository additionalServiceRepository;
	@Autowired
	private ModelMapperService mapper;
	@Autowired
	private AdditionalItemRepository additionalItemRepository;

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {
		AdditionalService addService = this.mapper.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);
		AdditionalItem item = additionalItemRepository.findById(createAdditionalServiceRequest.getAdditionalItemId()).get();
		double price = item.getPrice();
		int day = addService.getDay();
		addService.setTotalPrice(price * day);
		this.additionalServiceRepository.save(addService);
		return new SuccessResult(" id'li ek özellik başarıyla eklenmiştir.");
	}

	@Override
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
		additionalServiceRepository.deleteById(deleteAdditionalServiceRequest.getId());
		return new SuccessResult("li ek özellik başarıyla silinmiştir.");
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		AdditionalService addServiceToUpdate = mapper.forRequest().map(updateAdditionalServiceRequest, AdditionalService.class);
		additionalServiceRepository.save(addServiceToUpdate);
		return new SuccessResult(updateAdditionalServiceRequest.getId() + "li ek özellik başarıyla güncellenmiştir.");
	}

	@Override
	public DataResult<List<GetAllAdditionalServiceResponse>> getAll() {
		List<AdditionalService> items = this.additionalServiceRepository.findAll();
		List<GetAllAdditionalServiceResponse> responce = items.stream()
				.map(item -> this.mapper.forResponce().map(item, GetAllAdditionalServiceResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllAdditionalServiceResponse>>(responce);
	}

	@Override
	public DataResult<AdditionalService> getById(GetAdditionalServiceResponse getAdditionalServiceResponse) {
		AdditionalService item = this.mapper.forResponce().map(getAdditionalServiceResponse, AdditionalService.class);
		item = additionalServiceRepository.findById(getAdditionalServiceResponse.getId()).get();
		return new SuccessDataResult<AdditionalService>(item);
	}
}
