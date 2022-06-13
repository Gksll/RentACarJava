package com.kodlamaio.rentACar.Business.Concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.ColorService;
import com.kodlamaio.rentACar.Business.Requests.color.CreateColorRequest;
import com.kodlamaio.rentACar.Business.Requests.color.DeleteColorRequest;
import com.kodlamaio.rentACar.Business.Requests.color.UpdateColorRequest;
import com.kodlamaio.rentACar.Business.Responces.color.GetAllColorResponse;
import com.kodlamaio.rentACar.Business.Responces.color.GetColorResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessDataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.DataAccess.Abstracts.ColorRepository;
import com.kodlamaio.rentACar.Entities.Concretes.Color;

@Service
public class ColorManager implements ColorService {

	private ColorRepository colorRepository;
	private ModelMapperService modelmapperService;

	@Autowired
	public ColorManager(ColorRepository colorRepository, ModelMapperService modelmapperService) {
		this.colorRepository = colorRepository;
		this.modelmapperService = modelmapperService;
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) {
		checkIfColorExistsByName(createColorRequest.getName());
		Color color = this.modelmapperService.forRequest().map(createColorRequest, Color.class);
		colorRepository.save(color);
		return new SuccessResult(color.getName() + " başarıyla eklendi");

	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) {
		colorRepository.deleteById(deleteColorRequest.getId());
		return new SuccessResult();

	}

	@Override
	public DataResult<List<GetAllColorResponse>> getAll() {
		List<Color> colors = this.colorRepository.findAll();
		List<GetAllColorResponse> responce = colors.stream()
				.map(color -> this.modelmapperService.forResponce().map(color, GetAllColorResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllColorResponse>>(responce);
	}

	@Override
	public DataResult<Color> getById(GetColorResponse getColorResponse) {
		Color color = this.modelmapperService.forResponce().map(getColorResponse, Color.class);
		color=colorRepository.findById(getColorResponse.getId()).get();
		return new SuccessDataResult<Color>(color);
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) {
		Color colorToUpdate = this.modelmapperService.forRequest().map(updateColorRequest, Color.class);
		colorRepository.save(colorToUpdate);
		return new SuccessResult();
	}
	
	private void checkIfColorExistsByName(String name) 
	{
		Color currentColor = this.colorRepository.findByName(name);
		if (currentColor!=null) {
			throw new BusinessException("COLOR EXİSTS");
		}
		
	}
}
