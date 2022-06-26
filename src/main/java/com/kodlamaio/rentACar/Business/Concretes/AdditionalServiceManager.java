package com.kodlamaio.rentACar.Business.Concretes;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
import com.kodlamaio.rentACar.Core.Utilities.exceptions.BusinessException;
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
	private ModelMapperService ModelMapperService;
	@Autowired
	private AdditionalItemRepository additionalItemRepository;

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {
		checkIfAdditionalItemExistsById(createAdditionalServiceRequest.getAdditionalItemId());
		AdditionalService serviceToAdd = ModelMapperService.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);
		AdditionalItem item = additionalItemRepository.findById(serviceToAdd.getAdditionalItem().getId()).get();
		serviceToAdd.setDay(diffDay(createAdditionalServiceRequest.getSendDate(), createAdditionalServiceRequest.getReturnDate()));
		serviceToAdd.setTotalPrice(item.getPrice() * serviceToAdd.getDay());
		additionalServiceRepository.save(serviceToAdd);
		return new SuccessResult("added succesfully");
	}

	@Override
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
		checkIfAdditionalServiceExistsById(deleteAdditionalServiceRequest.getId());
		additionalServiceRepository.deleteById(deleteAdditionalServiceRequest.getId());
		return new SuccessResult("deleted succesfully");
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		checkIfAdditionalItemExistsById(updateAdditionalServiceRequest.getAdditionalItemId());
		checkIfAdditionalServiceExistsById(updateAdditionalServiceRequest.getId());
		AdditionalService addServiceToUpdate = ModelMapperService.forRequest().map(updateAdditionalServiceRequest, AdditionalService.class);
		additionalServiceRepository.save(addServiceToUpdate);
		return new SuccessResult("updated succesfully");
	}

	@Override
	public DataResult<List<GetAllAdditionalServiceResponse>> getAll() {
		List<AdditionalService> items = this.additionalServiceRepository.findAll();
		List<GetAllAdditionalServiceResponse> responce = items.stream()
				.map(item -> ModelMapperService.forResponce().map(item, GetAllAdditionalServiceResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllAdditionalServiceResponse>>(responce,"listed succesfully");
	}

	@Override
	public DataResult<AdditionalService> getById(GetAdditionalServiceResponse getAdditionalServiceResponse) {
		checkIfAdditionalServiceExistsById(getAdditionalServiceResponse.getId());
		AdditionalService item = ModelMapperService.forResponce().map(getAdditionalServiceResponse, AdditionalService.class);
		item = additionalServiceRepository.findById(getAdditionalServiceResponse.getId()).get();
		return new SuccessDataResult<AdditionalService>(item,"listed succesfully");
	}
	
	private void checkIfAdditionalServiceExistsById(int id) 
	{
		boolean result = additionalServiceRepository.existsById(id);
		if (result==false) {
			throw new BusinessException("ADDITIONAL SERVICE NOT EXISTS");
		}
	}
	
	private void checkIfAdditionalItemExistsById(int id) 
	{
		boolean result = additionalItemRepository.existsById(id);
		if (result==false) {
			throw new BusinessException("ADDITIONAL ITEM NOT EXISTS");
		}
	}
	private int diffDay(Date rentDate, Date returnDate) {
		long diff = returnDate.getTime() - rentDate.getTime();
		if (diff < 0) {
			throw new BusinessException("CHECK THE DATE");
		} else {
			long time = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			int days = (int) time;
			return days;
		}
	}
}
