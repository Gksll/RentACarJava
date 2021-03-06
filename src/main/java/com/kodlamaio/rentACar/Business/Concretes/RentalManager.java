package com.kodlamaio.rentACar.Business.Concretes;

import java.util.Date;
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
import com.kodlamaio.rentACar.Core.Utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.Core.webservices.FindexServiceAdapter;
import com.kodlamaio.rentACar.DataAccess.Abstracts.CarRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.IndividualCustomerRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.RentalRepository;
import com.kodlamaio.rentACar.Entities.Concretes.Car;
import com.kodlamaio.rentACar.Entities.Concretes.IndividualCustomer;
import com.kodlamaio.rentACar.Entities.Concretes.Rental;

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
	private IndividualCustomerRepository individualCustomerRepository;

	@Override
	public Result add(CreateRentalRequest createRentalRequest) {
		checkCar(createRentalRequest.getCarId()); // kiralanacak ara?? ger??ek mi?
		checkIfCustomersExistsById(createRentalRequest.getCustomerId());// Kiralayaca????m??z m????teri ger??ek mi?
		checkCarState(createRentalRequest.getCarId());// ara?? durumu m??sait mi?
		Rental rentalToAdd = this.modelmapperService.forRequest().map(createRentalRequest, Rental.class);
		IndividualCustomer individualCustomer = this.individualCustomerRepository.findById(createRentalRequest.getCustomerId()).get();
		Car carToGet = carRepository.findById(rentalToAdd.getCar().getId()).get();
		rentalToAdd.setTotalDays(diffDay(rentalToAdd.getPickupDate(), rentalToAdd.getReturnDate()));// tarih fark??
																									// hesapla
		checkFindexMinValue(carToGet.getMinFindexScore(), individualCustomer.getIdentityNumber());// findex puan kontrol??

		if (checkCarCityState(rentalToAdd.getCar().getId())) {

			rentalToAdd.setTotalPrice((rentalToAdd.getTotalDays() * carToGet.getDailyPrice()) + 750);

		} else {

			rentalToAdd.setTotalPrice((rentalToAdd.getTotalDays() * carToGet.getDailyPrice()));
		}
		rentalRepository.save(rentalToAdd);
		changeCarState(rentalToAdd.getCar().getId());// ara?? eklendikten sonra durumu kiraland?? olarak de??i??ir
		return new SuccessResult("rental added successfully");
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) {
		checkCar(updateRentalRequest.getCarId()); // g??ncellenecek ara?? kontorl??
		checkIfCustomersExistsById(updateRentalRequest.getCustomerId()); // g??ncellenecek m????teri kontorl??
		checkIfRentalsExistsById(updateRentalRequest.getId()); // g??ncelleme yapaca????m??z kiralama i??lemi kontrol??

		Rental rentalToUpdate = this.modelmapperService.forRequest().map(updateRentalRequest, Rental.class);
		Car carToGet = carRepository.findById(rentalToUpdate.getCar().getId()).get();
		rentalToUpdate.setTotalDays(diffDay(updateRentalRequest.getPickupDate(), updateRentalRequest.getReturnDate()));
		if (checkCarCityState(rentalToUpdate.getCar().getId())) {
			rentalToUpdate.setTotalPrice((rentalToUpdate.getTotalDays() * carToGet.getDailyPrice()) + 750);
		} else {
			rentalToUpdate.setTotalPrice((rentalToUpdate.getTotalDays() * carToGet.getDailyPrice()));
		}
		changeCarStateForUpdate(updateRentalRequest.getId());// ara?? de??i??irse ,eski ara?? durum de??i??tir
		rentalRepository.save(rentalToUpdate);
		changeCarState(rentalToUpdate.getCar().getId());// yeni eklenen arac??n durumunu de??i??tir.
		return new SuccessResult("rental updated successfully");
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		checkIfRentalsExistsById(deleteRentalRequest.getId());
		rentalRepository.deleteById(deleteRentalRequest.getId());
		return new SuccessResult("rental deleted successfully");
	}

	@Override
	public DataResult<Rental> getById(GetRentalResponse getRentalResponse) {
		checkIfRentalsExistsById(getRentalResponse.getId());
		Rental rental = this.modelmapperService.forResponce().map(getRentalResponse, Rental.class);
		rental = this.rentalRepository.findById(getRentalResponse.getId()).get();
		return new SuccessDataResult<Rental>(rental, "rental listed successfully");
	}

	@Override
	public DataResult<List<GetAllRentalResponse>> getAll() {
		List<Rental> rentals = this.rentalRepository.findAll();
		List<GetAllRentalResponse> responce = rentals.stream()
				.map(brand -> this.modelmapperService.forResponce().map(brand, GetAllRentalResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllRentalResponse>>(responce, "rentals listed successfully");
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

	// Ara?? alma b??rakma ??ehir kontrol?? sa??lar
	private boolean checkCarCityState(int carId) {
		boolean state = false;
		Car carToCheck = carRepository.findById(carId).get();
		if (carToCheck.getReturnCityId() != carToCheck.getPickupCityId()) {
			state = true;
		} else {
			state = false;
		}
		return state;
	}

	// Kiralama i??lemi daha ??nceden var m?? kontrol??n?? sa??lar
	private void checkIfRentalsExistsById(int id) {
		boolean result = rentalRepository.existsById(id);
		if (result == false) {
			throw new BusinessException("Rental NOT EXISTS");
		}
	}

	// G??ncellemelede ve eklemede olmayan m????teri kontrol?? sa??lar.
	private void checkIfCustomersExistsById(int id) {
		boolean result = individualCustomerRepository.existsById(id);
		if (result == false) {
			throw new BusinessException("CUSTOMER NOT EXISTS");
		}
	}

	// kiralama i??lemi sonras?? car state de??i??ir ??rn; "rented"
	private void changeCarState(int id) {
		Car carToUpdate = carRepository.findById(id).get();
		carToUpdate.setState("rented");
		carRepository.save(carToUpdate);
	}

	// kiralama g??ncellenirken ara?? de??i??tiyse eski arac?? rented' dan avaliable'a
	// ??eker
	private void changeCarStateForUpdate(int id) {
		Rental rentalForGetBeforeCarId = rentalRepository.findById(id).get();
		Car carToUpdate = carRepository.findById(rentalForGetBeforeCarId.getCar().getId()).get();
		carToUpdate.setState("avaliable");
		carRepository.save(carToUpdate);
	}
}
