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
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.Car;

@RestController
@RequestMapping("api/cars")
public class CarsController {
	
	private CarService carService;

	public CarsController(CarService carService) {
		this.carService = carService;
	}

	@PostMapping("/add")
	public Result Add(@RequestBody @Valid CreateCarRequest createcarRequest) {
		return	carService.add(createcarRequest);
	}

	@PostMapping("/update")
	public Result Update(@RequestBody UpdateCarRequest updatecarRequest) {
		return carService.update(updatecarRequest);
	}
	
	@PostMapping("/delete")
	public Result Delete(DeleteCarRequest deleteCarRequest) {
		return carService.delete(deleteCarRequest);
	}

	@GetMapping("/getById")
	public DataResult<Car> GetById( GetCarResponce getCarResponce) {
		return this.carService.getById(getCarResponce);
	}

	@GetMapping("/getAll")
	public DataResult<List<GetAllCarResponce>> GetAll() {
		return this.carService.getAll();
	}

}
