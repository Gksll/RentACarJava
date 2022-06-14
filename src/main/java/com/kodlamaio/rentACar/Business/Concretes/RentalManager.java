package com.kodlamaio.rentACar.Business.Concretes;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.RentalService;
import com.kodlamaio.rentACar.Business.Requests.rental.CreateRentalRequest;
import com.kodlamaio.rentACar.Business.Requests.rental.DeleteRentalRequest;
import com.kodlamaio.rentACar.Business.Requests.rental.UpdateRentalRequest;
import com.kodlamaio.rentACar.Business.Responces.rental.GetAllRentalResponse;
import com.kodlamaio.rentACar.Business.Responces.rental.GetRentalResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessDataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.DataAccess.Abstracts.CarRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.RentalRepository;
import com.kodlamaio.rentACar.Entities.Concretes.Car;
import com.kodlamaio.rentACar.Entities.Concretes.Rental;

@Service
public class RentalManager implements RentalService {
	
	@Autowired
	private RentalRepository rentalRepository;
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private ModelMapperService modelmapperService;

	@Override
	public Result add(CreateRentalRequest createRentalRequest) {
		
		Car car = new Car();
		Rental rentalToAdd = this.modelmapperService.forRequest().map(createRentalRequest, Rental.class);
		Car carToGetPrice = new Car();
//		AdditionalService serviceToGetTotalPrice=additionalServiceRepository.findById(createRentalRequest.getAdditionalServiceId()).get();
		long diff = createRentalRequest.getReturnDate().getTime() - createRentalRequest.getPickupDate().getTime();
		long time = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		carToGetPrice = carRepository.findById(createRentalRequest.getCarId()).get();
		car.setId(createRentalRequest.getCarId());
		rentalToAdd.setCar(car);
		rentalToAdd.setTotalDays((int) time);
		if (carToGetPrice.getReturnCityId() == carToGetPrice.getPickupCityId()) {
			rentalToAdd.setTotalPrice((rentalToAdd.getTotalDays() * carToGetPrice.getDailyPrice()));
		} else {
			rentalToAdd.setTotalPrice((rentalToAdd.getTotalDays() * carToGetPrice.getDailyPrice())+ 750);
		}

		rentalRepository.save(rentalToAdd);
		return new SuccessResult();
	}

	// Update edilirken fiyat bilgisi gelmiyor, bağlı nesneleri kontrol et
	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) {
		Rental rentalToUpdate = this.modelmapperService.forRequest().map(updateRentalRequest, Rental.class);
		rentalRepository.save(rentalToUpdate);
		return new SuccessResult();
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		rentalRepository.deleteById(deleteRentalRequest.getId());
		return new SuccessResult();
	}

	@Override
	public DataResult<Rental> getById(GetRentalResponse getRentalResponse) {
		Rental rental = this.modelmapperService.forResponce().map(getRentalResponse, Rental.class);
		rental = this.rentalRepository.findById(getRentalResponse.getId()).get();
		return new SuccessDataResult<Rental>(rental);
	}

	@Override
	public DataResult<List<GetAllRentalResponse>> getAll() {
		List<Rental> rentals = this.rentalRepository.findAll();
		List<GetAllRentalResponse> responce = rentals.stream()
				.map(brand -> this.modelmapperService.forResponce().map(brand, GetAllRentalResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllRentalResponse>>(responce);
	}

}
