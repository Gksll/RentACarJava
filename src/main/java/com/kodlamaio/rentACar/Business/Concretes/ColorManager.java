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
	@Autowired
	private ColorRepository colorRepository;
	@Autowired
	private ModelMapperService modelmapperService;
	
	@Override
	public Result add(CreateColorRequest createColorRequest) {
		checkIfColorExistsByName(createColorRequest.getName());
		Color color = modelmapperService.forRequest().map(createColorRequest, Color.class);
		colorRepository.save(color);
		return new SuccessResult(color.getName()+" added successfully");
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) {
		checkIfColorExistsById(deleteColorRequest.getId());
		colorRepository.deleteById(deleteColorRequest.getId());
		return new SuccessResult(deleteColorRequest.getId()+" deleted successfully");
	}

	@Override
	public DataResult<List<GetAllColorResponse>> getAll() {
		List<Color> colors = colorRepository.findAll();
		List<GetAllColorResponse> responce = colors.stream()
				.map(color -> modelmapperService.forResponce().map(color, GetAllColorResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllColorResponse>>(responce,"the colors successfully listed");
	}

	@Override
	public DataResult<Color> getById(GetColorResponse getColorResponse) {
		checkIfColorExistsById(getColorResponse.getId());
		Color color = modelmapperService.forResponce().map(getColorResponse, Color.class);
		color=colorRepository.findById(getColorResponse.getId()).get();
		return new SuccessDataResult<Color>(color,"the color was successfully listed");
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) {
		checkIfColorExistsById(updateColorRequest.getId());
		Color colorToUpdate = modelmapperService.forRequest().map(updateColorRequest, Color.class);
		colorRepository.save(colorToUpdate);
		return new SuccessResult("the color was successfully updated");
	}
	
	private void checkIfColorExistsByName(String name) 
	{
		Color currentColor = colorRepository.findByName(name);
		if (currentColor!=null) {
			throw new BusinessException("COLOR EXISTS");
		}
	}
	private void checkIfColorExistsById(int id) 
	{
		boolean result = colorRepository.existsById(id);
		if (result==false) {
			throw new BusinessException("COLOR NOT EXISTS");
		}
	}
}
