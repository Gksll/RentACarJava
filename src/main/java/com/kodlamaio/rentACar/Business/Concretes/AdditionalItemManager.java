package com.kodlamaio.rentACar.Business.Concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.AdditionalItemService;
import com.kodlamaio.rentACar.Business.Requests.additionalItem.CreateAdditionalItemRequest;
import com.kodlamaio.rentACar.Business.Requests.additionalItem.DeleteAdditionalItemRequest;
import com.kodlamaio.rentACar.Business.Requests.additionalItem.UpdateAdditionalItemRequest;
import com.kodlamaio.rentACar.Business.Responces.additionalItem.GetAdditionalItemResponse;
import com.kodlamaio.rentACar.Business.Responces.additionalItem.GetAllAdditionalItemResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessDataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.DataAccess.Abstracts.AdditionalItemRepository;
import com.kodlamaio.rentACar.Entities.Concretes.AdditionalItem;

@Service
public class AdditionalItemManager implements AdditionalItemService {
	@Autowired
	private AdditionalItemRepository additionalItemRepository;
	@Autowired
	private ModelMapperService modelMapperService;


	@Override
	public Result add(CreateAdditionalItemRequest createAdditionalItemRequest) {
		checkIfAdditionalItemExistsByName(createAdditionalItemRequest.getName());
		AdditionalItem addItem = modelMapperService.forRequest().map(createAdditionalItemRequest, AdditionalItem.class);
		this.additionalItemRepository.save(addItem);
		return new SuccessResult(addItem.getName() + " added successfull");
	}

	@Override
	public Result delete(DeleteAdditionalItemRequest deleteAdditionalItemRequest) {
		checkIfAdditionalItemExistsById(deleteAdditionalItemRequest.getId());
		additionalItemRepository.deleteById(deleteAdditionalItemRequest.getId());
		return new SuccessResult(deleteAdditionalItemRequest.getId() + "deleted successfull");
	}

	@Override
	public Result update(UpdateAdditionalItemRequest updateAdditionalItemRequest) {
		checkIfAdditionalItemExistsById(updateAdditionalItemRequest.getId());
		AdditionalItem addItemToUpdate = modelMapperService.forRequest().map(updateAdditionalItemRequest, AdditionalItem.class);
		additionalItemRepository.save(addItemToUpdate);
		return new SuccessResult(updateAdditionalItemRequest.getId() + "updated successfull");
	}

	@Override
	public DataResult<List<GetAllAdditionalItemResponse>> getAll() {
		List<AdditionalItem> items = this.additionalItemRepository.findAll();
		List<GetAllAdditionalItemResponse> responce = items.stream()
				.map(item -> modelMapperService.forResponce().map(item, GetAllAdditionalItemResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllAdditionalItemResponse>>(responce,"listed successfull");
	}

	@Override
	public DataResult<AdditionalItem> getById(GetAdditionalItemResponse getAdditionalItemResponse) {
		checkIfAdditionalItemExistsById(getAdditionalItemResponse.getId());
		AdditionalItem item = modelMapperService.forResponce().map(getAdditionalItemResponse, AdditionalItem.class);
		item = additionalItemRepository.findById(getAdditionalItemResponse.getId()).get();
		return new SuccessDataResult<AdditionalItem>(item,"listed successfull");
	}
	private void checkIfAdditionalItemExistsByName(String name) 
	{
		AdditionalItem currentItem = this.additionalItemRepository.getByName(name);
		if (currentItem!=null) {
			throw new BusinessException("ADDITIONAL ITEM EXİSTS");
		}
		
	}
	private void checkIfAdditionalItemExistsById(int id) 
	{
		boolean result = additionalItemRepository.existsById(id);
		if (result==false) {
			throw new BusinessException("ADDITIONAL ITEM NOT EXISTS");
		}
	}

}
