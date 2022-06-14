package com.kodlamaio.rentACar.Api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.CarService;
import com.kodlamaio.rentACar.Business.Requests.car.CreateCarRequest;
import com.kodlamaio.rentACar.Business.Requests.car.DeleteCarRequest;
import com.kodlamaio.rentACar.Business.Requests.car.UpdateCarRequest;
import com.kodlamaio.rentACar.Business.Responces.car.GetAllCarResponce;
import com.kodlamaio.rentACar.Business.Responces.car.GetCarResponce;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.ErrorResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Entities.Concretes.Car;

import lombok.var;

@RestController
@RequestMapping("api/cars")
public class CarsController {
	private CarService carService;

	public CarsController(CarService carService) {
		this.carService = carService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarRequest createcarRequest) {
		var result = carService.add(createcarRequest);
		if (result.isSuccess()) {
			return new SuccessResult();
		}
		return new ErrorResult();

	}

	@PostMapping("/update")
	public Result update(@RequestBody UpdateCarRequest updatecarRequest) {
		var result = carService.update(updatecarRequest);
		if (result.isSuccess()) {
			return new SuccessResult("güncelledim");
		}
		return new ErrorResult("hata");
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteCarRequest deleteCarRequest) {
		carService.delete(deleteCarRequest);
		return new SuccessResult();
	}

	@GetMapping("/getbyid")
	public DataResult<Car> getById(@RequestBody GetCarResponce getCarResponce) {
		return this.carService.getById(getCarResponce);
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllCarResponce>> getAll() {
		return this.carService.getAll();
	}

}
