package com.kodlamaio.rentACar.Api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.ColorService;
import com.kodlamaio.rentACar.Business.Requests.color.CreateColorRequest;
import com.kodlamaio.rentACar.Business.Requests.color.DeleteColorRequest;
import com.kodlamaio.rentACar.Business.Requests.color.UpdateColorRequest;
import com.kodlamaio.rentACar.Business.Responces.color.GetAllColorResponse;
import com.kodlamaio.rentACar.Business.Responces.color.GetColorResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.Color;

@RestController
@RequestMapping("api/colors")
public class ColorsController {

	private ColorService colorService;

	public ColorsController(ColorService colorService) {
		this.colorService = colorService;
	}

	@PostMapping("/add")
	public Result Add(@RequestBody CreateColorRequest createColorRequest) {
		return colorService.add(createColorRequest);
	}

	@PostMapping("/delete")
	public Result Delete(DeleteColorRequest deleteColorRequest) {
		return colorService.delete(deleteColorRequest);
	}

	@PostMapping("/update")
	public Result Update(@RequestBody UpdateColorRequest updateColorRequest) {
		return colorService.update(updateColorRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<GetAllColorResponse>> GetAll() {
		return colorService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<Color> GetById(GetColorResponse getColorResponse) {
		return colorService.getById(getColorResponse);
	}
}
