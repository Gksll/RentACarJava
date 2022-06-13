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
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Entities.Concretes.Color;

@RestController
@RequestMapping("api/colors")
public class ColorsController {
	private ColorService colorService;

	public ColorsController(ColorService colorService) {
		this.colorService = colorService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateColorRequest createColorRequest) {
		colorService.add(createColorRequest);
		return new SuccessResult();
	}
	@PostMapping("/delete")
	public Result delete(DeleteColorRequest deleteColorRequest) {
		colorService.delete(deleteColorRequest);
		return new SuccessResult();
	}

	@PostMapping("/update")
	public Result update(@RequestBody UpdateColorRequest updateColorRequest) {
		colorService.update(updateColorRequest);
		return new SuccessResult();
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllColorResponse>> GetAll() {
		return this.colorService.getAll();
//		return new SuccessDataResult<List<GetAllColorResponse>>(colorService.getAll().getData());
	}
	@GetMapping("/getbyid")
	public DataResult<Color> GetById(GetColorResponse getColorResponse) {
		return this.colorService.getById(getColorResponse);
//		return new SuccessDataResult<Color>(colorService.getById(id).getData());
	}
}
