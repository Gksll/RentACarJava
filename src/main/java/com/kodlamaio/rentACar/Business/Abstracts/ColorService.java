package com.kodlamaio.rentACar.Business.Abstracts;

import java.util.List;

import com.kodlamaio.rentACar.Business.Requests.color.CreateColorRequest;
import com.kodlamaio.rentACar.Business.Requests.color.DeleteColorRequest;
import com.kodlamaio.rentACar.Business.Requests.color.UpdateColorRequest;
import com.kodlamaio.rentACar.Business.Responces.color.GetAllColorResponse;
import com.kodlamaio.rentACar.Business.Responces.color.GetColorResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.Color;

public interface ColorService {
	Result add(CreateColorRequest createColorRequest);

	Result delete(DeleteColorRequest deleteColorRequest);

	Result update(UpdateColorRequest updateColorRequest);

	DataResult<List<GetAllColorResponse>> getAll();

	DataResult<Color> getById(GetColorResponse getColorResponse);
}
