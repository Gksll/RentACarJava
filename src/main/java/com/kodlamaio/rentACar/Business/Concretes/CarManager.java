package com.kodlamaio.rentACar.Business.Concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.CarService;
import com.kodlamaio.rentACar.Business.Requests.car.CreateCarRequest;
import com.kodlamaio.rentACar.Business.Requests.car.DeleteCarRequest;
import com.kodlamaio.rentACar.Business.Requests.car.UpdateCarRequest;
import com.kodlamaio.rentACar.Business.Responces.car.GetAllCarResponce;
import com.kodlamaio.rentACar.Business.Responces.car.GetCarResponce;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.ErrorResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessDataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.DataAccess.Abstracts.CarRepository;
import com.kodlamaio.rentACar.Entities.Concretes.Car;

@Service
public class CarManager implements CarService {
	private CarRepository carRepository;
	private ModelMapperService modelmapperService;
	

	@Autowired
	public CarManager(CarRepository carRepository, ModelMapperService modelmapperService) {
		this.carRepository = carRepository;
		this.modelmapperService = modelmapperService;
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {

		Car car = this.modelmapperService.forRequest().map(createCarRequest, Car.class);

		if (checkBrands(car) < 5) {

			carRepository.save(car);
			return new SuccessResult();
		}

		else {

			return new ErrorResult("en fazla 5");
		}
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {
		carRepository.deleteById(deleteCarRequest.getId());
		return new SuccessResult();
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {
		Car carToUpdate = this.modelmapperService.forRequest().map(updateCarRequest, Car.class);
//		carToUpdate=carRepository.findById(updateCarRequest.getId()).get();
		carRepository.save(carToUpdate);
		return new SuccessResult();
	}

	@Override
	public DataResult<Car> getById(GetCarResponce getCarResponce) {
		Car carToGet = this.modelmapperService.forResponce().map(getCarResponce, Car.class);
		carToGet = this.carRepository.findById(getCarResponce.getId()).get();
		return new SuccessDataResult<Car>(carToGet);
	}

	

	@Override
	public int checkBrands(Car car) {
		int count = 0;
		for (Car item : carRepository.findAll()) {
			if (item.getBrand().getId() == car.getBrand().getId()) {
				count++;
			}
		}
		System.out.println(count);
		return count;
	}

	@Override
	public DataResult<List<GetAllCarResponce>> getAll() {
		List<Car> cars = this.carRepository.findAll();
		List<GetAllCarResponce> responce = cars.stream()
				.map(Car -> this.modelmapperService.forResponce().map(Car, GetAllCarResponce.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllCarResponce>>(responce);
	}

	


}
