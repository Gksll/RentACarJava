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
import com.kodlamaio.rentACar.Core.webservices.FindexServiceAdapter;
import com.kodlamaio.rentACar.DataAccess.Abstracts.CarRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.RentalRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.CustomerRepository;
import com.kodlamaio.rentACar.Entities.Concretes.Car;
import com.kodlamaio.rentACar.Entities.Concretes.Rental;
import com.kodlamaio.rentACar.Entities.Concretes.Customer;

@Service
public class RentalManager implements RentalService {

	@Autowired
	private RentalRepository rentalRepository;
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private ModelMapperService modelmapperService;
	@Autowired
	private FindexServiceAdapter adapter;
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Result add(CreateRentalRequest createRentalRequest) {

		Rental rentalToAdd = this.modelmapperService.forRequest().map(createRentalRequest, Rental.class);
		Car carToGet = carRepository.findById(createRentalRequest.getCarId()).get();
		Customer customer = this.customerRepository.findById(createRentalRequest.getCustomerId()).get();
		long diff = createRentalRequest.getReturnDate().getTime() - createRentalRequest.getPickupDate().getTime();
		long time = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		rentalToAdd.setCar(carToGet);
		rentalToAdd.setTotalDays((int) time);
		if (carToGet.getReturnCityId() == carToGet.getPickupCityId() && carToGet.getState() == "avaliable") {

			rentalToAdd.setTotalPrice((rentalToAdd.getTotalDays() * carToGet.getDailyPrice()));
		}
		else {
			rentalToAdd.setTotalPrice((rentalToAdd.getTotalDays() * carToGet.getDailyPrice()) + 750);
		}
		if (checkFindexMinValue(carToGet.getMinFindexScore(), customer.getTcNo())) {
			carToGet.setState("rented");
			rentalRepository.save(rentalToAdd);
			return new Result(true, "eklendi");
		}
		else {
			return new Result(false, "findex puanı yetersiz");
		}
	}

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

	public boolean checkFindexMinValue(int score, String tc) {
		boolean state = false;
		if (adapter.CheckFindexScore(tc) > score) {
			state = true;
		} else {
			state = false;
		}
		return state;
	}
}
