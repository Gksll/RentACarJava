package com.kodlamaio.rentACar.Business.Concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import com.kodlamaio.rentACar.Core.Utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.DataAccess.Abstracts.BrandRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.CarRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.ColorRepository;
import com.kodlamaio.rentACar.Entities.Concretes.Brand;
import com.kodlamaio.rentACar.Entities.Concretes.Car;

@Service
public class CarManager implements CarService {
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private ColorRepository colorRepository;
	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private ModelMapperService modelmapperService;

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		checkIfColorExistsById(createCarRequest.getColorId());
		checkIfBrandExistsById(createCarRequest.getBrandId());
		checkIfBrandLimitExceed(createCarRequest.getBrandId());
		Car car = this.modelmapperService.forRequest().map(createCarRequest, Car.class);
		carRepository.save(car);
		return new SuccessResult("car added successfully");
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {
		checkIfCarExistsById(deleteCarRequest.getId());
		carRepository.deleteById(deleteCarRequest.getId());
		return new SuccessResult("id: " + deleteCarRequest.getId() + " deleted successfully");
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {

		checkIfCarExistsById(updateCarRequest.getId());
		checkIfColorExistsById(updateCarRequest.getColorId());
		checkIfBrandExistsById(updateCarRequest.getBrandId());
		checkIfBrandLimitExceed(updateCarRequest.getBrandId());
		Brand brandToCheck = brandRepository.findById(updateCarRequest.getBrandId()).get();
		Car carToUpdate = this.modelmapperService.forRequest().map(updateCarRequest, Car.class);
		if (brandToCheck.getId() == updateCarRequest.getBrandId()) {
			carRepository.save(carToUpdate);
			return new SuccessResult("the car was successfully updated");
		}
		return new ErrorResult("there can be no more than five cars of the same brand");
	}
	
	@Override
	public DataResult<Car> getById(GetCarResponce getCarResponce) {
		checkIfCarExistsById(getCarResponce.getId());
		Car carToGet = this.modelmapperService.forResponce().map(getCarResponce, Car.class);
		carToGet = this.carRepository.findById(getCarResponce.getId()).get();
		return new SuccessDataResult<Car>(carToGet, "the car was successfully listed");
	}
	@Cacheable("cars")
	@Override
	
	public DataResult<List<GetAllCarResponce>> getAll() {
		List<Car> cars = this.carRepository.findAll();
		List<GetAllCarResponce> responce = cars.stream()
				.map(Car -> this.modelmapperService.forResponce().map(Car, GetAllCarResponce.class))
				.collect(Collectors.toList());
		try {
			Thread.sleep(1000 * 4);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new SuccessDataResult<List<GetAllCarResponce>>(responce, "the cars successfully listed");
	}

	private void checkIfBrandLimitExceed(int id) {
		List<Car> result = carRepository.getByBrandId(id);
		if (result.size() > 5) {
			throw new BusinessException("THERE CAN BE NO MORE THAN FIVE CARS OF THE SAME BRAND");
		}
	}

	private void checkIfCarExistsById(int id) {
		boolean result = carRepository.existsById(id);
		if (result == false) {
			throw new BusinessException("CAR NOT EXISTS");
		}
	}

	private void checkIfColorExistsById(int id) {
		boolean result = colorRepository.existsById(id);
		if (result == false) {
			throw new BusinessException("COLOR NOT EXISTS");
		}
	}

	private void checkIfBrandExistsById(int id) {
		boolean result = brandRepository.existsById(id);
		if (result == false) {
			throw new BusinessException("BRAND NOT EXISTS");
		}
	}
	
	public Result MappingExample(CreateCarRequest createCarRequest) {
		checkIfColorExistsById(createCarRequest.getColorId());
		checkIfBrandExistsById(createCarRequest.getBrandId());
		checkIfBrandLimitExceed(createCarRequest.getBrandId());
//		Car car = this.modelmapperService.forRequest().map(createCarRequest, Car.class);
		Car car = Car.builder()
				.dailyPrice(createCarRequest.getDailyPrice())
				.description(createCarRequest.getDescription())
				.state(createCarRequest.getState())
				.plaque(createCarRequest.getPlaque())
				.minFindexScore(createCarRequest.getMinFindexScore())
				.kmCount(createCarRequest.getKmCount())
				.color(colorRepository.findById(createCarRequest.getColorId()).get())
				.brand(brandRepository.findById(createCarRequest.getBrandId()).get())
				.build();
		carRepository.save(car);
		return new SuccessResult("car added successfully");
	}
}
