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
		checkCar(createRentalRequest.getCarId()); // kiralanacak araç gerçek mi?
		checkIfCustomersExistsById(createRentalRequest.getCustomerId());// Kiralayacağımız müşteri gerçek mi?
		checkCarState(createRentalRequest.getCarId());// araç durumu müsait mi?
		Rental rentalToAdd = this.modelmapperService.forRequest().map(createRentalRequest, Rental.class);
		Customer customer = this.customerRepository.findById(createRentalRequest.getCustomerId()).get();
		Car carToGet = carRepository.findById(rentalToAdd.getCar().getId()).get();
		rentalToAdd.setTotalDays(diffDay(rentalToAdd.getPickupDate(), rentalToAdd.getReturnDate()));// tarih farkı
																									// hesapla
		checkFindexMinValue(carToGet.getMinFindexScore(), customer.getTcNo());// findex puan kontrolü

		if (checkCarCityState(rentalToAdd.getCar().getId())) {

			rentalToAdd.setTotalPrice((rentalToAdd.getTotalDays() * carToGet.getDailyPrice()) + 750);

		} else {

			rentalToAdd.setTotalPrice((rentalToAdd.getTotalDays() * carToGet.getDailyPrice()));
		}
		rentalRepository.save(rentalToAdd);
		changeCarState(rentalToAdd.getCar().getId());// araç eklendikten sonra durumu kiralandı olarak değişir
		return new SuccessResult("rental added successfully");
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) {
		checkCar(updateRentalRequest.getCarId()); // güncellenecek araç kontorlü
		checkIfCustomersExistsById(updateRentalRequest.getCustomerId()); // güncellenecek müşteri kontorlü
		checkIfRentalsExistsById(updateRentalRequest.getId()); // güncelleme yapacağımız kiralama işlemi kontrolü

		Rental rentalToUpdate = this.modelmapperService.forRequest().map(updateRentalRequest, Rental.class);
		Car carToGet = carRepository.findById(rentalToUpdate.getCar().getId()).get();
		rentalToUpdate.setTotalDays(diffDay(updateRentalRequest.getPickupDate(), updateRentalRequest.getReturnDate()));
		if (checkCarCityState(rentalToUpdate.getCar().getId())) {

			rentalToUpdate.setTotalPrice((rentalToUpdate.getTotalDays() * carToGet.getDailyPrice()) + 750);

		} else {

			rentalToUpdate.setTotalPrice((rentalToUpdate.getTotalDays() * carToGet.getDailyPrice()));
		}
		changeCarStateForUpdate(updateRentalRequest.getId());// araç değişirse ,eski araç durum değiştir
		rentalRepository.save(rentalToUpdate);
		changeCarState(rentalToUpdate.getCar().getId());// yeni eklenen aracın durumunu değiştir.
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
			throw new BusinessException("FINDEX SCORE NOT ENOUGHG TO RENT THİS CAR");
		}
	}

	// Tarihler arası gün farkını hesaplar
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

	// Var olmayan araç kiraya gönderilemez, kontrolü sağlandı.
	private void checkCar(int carId) {
		boolean result = carRepository.existsById(carId);
		if (result == false) {
			throw new BusinessException("THE CAR DOESNT EXIST");
		}
	}

	// Kiralama eklemeden aracın state bilgisini kontrol eder.
	private void checkCarState(int carId) {
		Car carToCheck = carRepository.findById(carId).get();
		if (carToCheck.getState().equals("avaliable")) {
		} else {
			throw new BusinessException("THE CAR DOESNT READY TO RENT, CHECK CAR STATE");
		}
	}

	// Araç alma bırakma şehir kontrolü sağlar
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

	// Kiralama işlemi daha önceden var mı kontrolünü sağlar
	private void checkIfRentalsExistsById(int id) {
		boolean result = rentalRepository.existsById(id);
		if (result == false) {
			throw new BusinessException("Rental NOT EXISTS");
		}
	}

	// Güncellemelede ve eklemede olmayan müşteri kontrolü sağlar.
	private void checkIfCustomersExistsById(int id) {
		boolean result = customerRepository.existsById(id);
		if (result == false) {
			throw new BusinessException("CUSTOMER NOT EXISTS");
		}
	}

	// kiralama işlemi sonrası car state değişir örn; "rented"
	private void changeCarState(int id) {
		Car carToUpdate = carRepository.findById(id).get();
		carToUpdate.setState("rented");
		carRepository.save(carToUpdate);
	}

	// kiralama güncellenirken araç değiştiyse eski aracı rented' dan avaliable'a
	// çeker
	private void changeCarStateForUpdate(int id) {
		Rental rentalForGetBeforeCarId = rentalRepository.findById(id).get();
		Car carToUpdate = carRepository.findById(rentalForGetBeforeCarId.getCar().getId()).get();
		carToUpdate.setState("avaliable");
		carRepository.save(carToUpdate);
	}
}
