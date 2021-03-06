package com.kodlamaio.rentACar.Business.Concretes;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.IndividualRentalService;
import com.kodlamaio.rentACar.Business.Requests.individualRental.CreateIndividualRentalRequest;
import com.kodlamaio.rentACar.Business.Requests.individualRental.DeleteIndividualRentalRequest;
import com.kodlamaio.rentACar.Business.Requests.individualRental.UpdateIndividualRentalRequest;
import com.kodlamaio.rentACar.Business.Responces.individualRental.GetAllIndividualRentalResponse;
import com.kodlamaio.rentACar.Business.Responces.individualRental.GetIndividualRentalResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessDataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.Core.webservices.FindexServiceAdapter;
import com.kodlamaio.rentACar.DataAccess.Abstracts.CarRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.IndividualCustomerRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.IndividualRentalRepository;
import com.kodlamaio.rentACar.Entities.Concretes.Car;
import com.kodlamaio.rentACar.Entities.Concretes.IndividualCustomer;
import com.kodlamaio.rentACar.Entities.Concretes.IndividualRental;

@Service
public class IndividualRentalManager implements IndividualRentalService {

	@Autowired
	private IndividualRentalRepository individualRentalRepository;
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private ModelMapperService modelmapperService;
	@Autowired
	private FindexServiceAdapter adapter;
	@Autowired
	private IndividualCustomerRepository individualCustomerRepository;

	
	
	@Override
	public Result add(CreateIndividualRentalRequest createIndividualRentalRequest) {
		checkCar(createIndividualRentalRequest.getCarId()); 
		checkIfCustomersExistsById(createIndividualRentalRequest.getIndividualCustomerId());
		checkCarState(createIndividualRentalRequest.getCarId());
		IndividualRental rentalToAdd = this.modelmapperService.forRequest().map(createIndividualRentalRequest, IndividualRental.class);
		IndividualCustomer individualCustomer = this.individualCustomerRepository.findById(createIndividualRentalRequest.getIndividualCustomerId()).get();
		Car carToGet = carRepository.findById(rentalToAdd.getCar().getId()).get();
		rentalToAdd.setTotalDays(diffDay(rentalToAdd.getPickupDate(), rentalToAdd.getReturnDate()));																			
		checkFindexMinValue(carToGet.getMinFindexScore(), individualCustomer.getIdentityNumber());
		rentalToAdd.setTotalPrice(calculateTotalPrice( rentalToAdd.getTotalDays(),carToGet ,rentalToAdd ));;
		
		individualRentalRepository.save(rentalToAdd);
		changeCarState(rentalToAdd.getCar().getId());// ara?? eklendikten sonra durumu kiraland?? olarak de??i??ir
		return new SuccessResult("rental added successfully");
	}

	@Override
	public Result update(UpdateIndividualRentalRequest updateIndividualRentalRequest) {
		checkCar(updateIndividualRentalRequest.getCarId()); // g??ncellenecek ara?? kontorl??
		checkIfCustomersExistsById(updateIndividualRentalRequest.getIndividualCustomerId()); // g??ncellenecek m????teri kontorl??
		checkIfRentalsExistsById(updateIndividualRentalRequest.getId()); // g??ncelleme yapaca????m??z kiralama i??lemi kontrol??

		IndividualRental rentalToUpdate = this.modelmapperService.forRequest().map(updateIndividualRentalRequest, IndividualRental.class);
		Car carToGet = carRepository.findById(rentalToUpdate.getCar().getId()).get();
		rentalToUpdate.setTotalDays(diffDay(updateIndividualRentalRequest.getPickupDate(), updateIndividualRentalRequest.getReturnDate()));
		calculateTotalPrice( rentalToUpdate.getTotalDays(),carToGet ,rentalToUpdate );
		changeCarStateForUpdate(updateIndividualRentalRequest.getId());// ara?? de??i??irse ,eski ara?? durum de??i??tir
		individualRentalRepository.save(rentalToUpdate);
		changeCarState(rentalToUpdate.getCar().getId());// yeni eklenen arac??n durumunu de??i??tir.
		return new SuccessResult("rental updated successfully");
	}

	@Override
	public Result delete(DeleteIndividualRentalRequest deleteIndividualRentalRequest) {
		checkIfRentalsExistsById(deleteIndividualRentalRequest.getId());
		individualRentalRepository.deleteById(deleteIndividualRentalRequest.getId());
		return new SuccessResult("rental deleted successfully");
	}

	@Override
	public DataResult<IndividualRental> getById(GetIndividualRentalResponse getIndividualRentalResponse) {
		checkIfRentalsExistsById(getIndividualRentalResponse.getId());
		IndividualRental individualRental = this.modelmapperService.forResponce().map(getIndividualRentalResponse, IndividualRental.class);
		individualRental = this.individualRentalRepository.findById(getIndividualRentalResponse.getId()).get();
		return new SuccessDataResult<IndividualRental>(individualRental, "rental listed successfully");
	}

	@Override
	public DataResult<List<GetAllIndividualRentalResponse>> getAll() {
		List<IndividualRental> individualRentals = this.individualRentalRepository.findAll();
		List<GetAllIndividualRentalResponse> responce = individualRentals.stream()
				.map(brand -> this.modelmapperService.forResponce().map(brand, GetAllIndividualRentalResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllIndividualRentalResponse>>(responce, "individualRentals listed successfully");
	}

	private void checkFindexMinValue(int score, String tc) {
		if (adapter.CheckFindexScore(tc) < score) {
			throw new BusinessException("FINDEX SCORE NOT ENOUGHG TO RENT TH??S CAR");
		}
	}

	// Tarihler aras?? g??n fark??n?? hesaplar
	private int diffDay(Date pickupDate, Date returnDate) {
		long diff = returnDate.getTime() - pickupDate.getTime();
		if (diff < 0) {
			throw new BusinessException("CHECK THE DATE");
		} else {
			long time = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			int days = (int) time;
			return days;
		}
	}

	// Var olmayan ara?? kiraya g??nderilemez, kontrol?? sa??land??.
	private void checkCar(int carId) {
		boolean result = carRepository.existsById(carId);
		if (result == false) {
			throw new BusinessException("THE CAR DOESNT EXIST");
		}
	}

	// Kiralama eklemeden arac??n state bilgisini kontrol eder.
	private void checkCarState(int carId) {
		Car carToCheck = carRepository.findById(carId).get();
		if (carToCheck.getState().equals("avaliable")) {
		} else {
			throw new BusinessException("THE CAR DOESNT READY TO RENT, CHECK CAR STATE");
		}
	}

	private void checkIfRentalsExistsById(int id) {
		boolean result = individualRentalRepository.existsById(id);
		if (result == false) {
			throw new BusinessException("IndividualRental NOT EXISTS");
		}
	}

	private void checkIfCustomersExistsById(int id) {
		boolean result = individualCustomerRepository.existsById(id);
		if (result == false) {
			throw new BusinessException("CUSTOMER NOT EXISTS");
		}
	}

	private void changeCarState(int id) {
		Car carToUpdate = carRepository.findById(id).get();
		carToUpdate.setState("rented");
		carRepository.save(carToUpdate);
	}

	private void changeCarStateForUpdate(int id) {
		IndividualRental rentalForGetBeforeCarId = individualRentalRepository.findById(id).get();
		Car carToUpdate = carRepository.findById(rentalForGetBeforeCarId.getCar().getId()).get();
		carToUpdate.setState("avaliable");
		carRepository.save(carToUpdate);
	}
	private double calculateTotalPrice(int totalDays, Car carToCalculate, IndividualRental rentalToCalculate) {
		double totalPrice = totalDays * carToCalculate.getDailyPrice();

		if (rentalToCalculate.getPickupCityId()!=(rentalToCalculate.getReturnCityId())) {
			totalPrice += 750;
		}
		return totalPrice;
	}
}
