package com.kodlamaio.rentACar.Business.Concretes;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.CorporateRentalService;
import com.kodlamaio.rentACar.Business.Requests.corporateRental.CreateCorporateRentalRequest;
import com.kodlamaio.rentACar.Business.Requests.corporateRental.DeleteCorporateRentalRequest;
import com.kodlamaio.rentACar.Business.Requests.corporateRental.UpdateCorporateRentalRequest;
import com.kodlamaio.rentACar.Business.Responces.corporateRental.GetAllCorporateRentalResponse;
import com.kodlamaio.rentACar.Business.Responces.corporateRental.GetCorporateRentalResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessDataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.DataAccess.Abstracts.CarRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.CorporateCustomerRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.CorporateRentalRepository;
import com.kodlamaio.rentACar.Entities.Concretes.Car;
import com.kodlamaio.rentACar.Entities.Concretes.CorporateRental;

@Service
public class CorporateRentalManager implements CorporateRentalService {

	@Autowired
	private CorporateRentalRepository corporateRentalRepository;
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private ModelMapperService modelmapperService;
	@Autowired
	private CorporateCustomerRepository corporateCustomerRepository;

	@Override
	public Result add(CreateCorporateRentalRequest createCorporateRentalRequest) {
		checkCar(createCorporateRentalRequest.getCarId()); // kiralanacak ara?? ger??ek mi?
		checkIfCustomersExistsById(createCorporateRentalRequest.getCorporateCustomerId());// Kiralayaca????m??z m????teri ger??ek mi?
		checkCarState(createCorporateRentalRequest.getCarId());// ara?? durumu m??sait mi?
		CorporateRental rentalToAdd = this.modelmapperService.forRequest().map(createCorporateRentalRequest, CorporateRental.class);
		Car carToGet = carRepository.findById(rentalToAdd.getCar().getId()).get();
		rentalToAdd.setTotalDays(diffDay(rentalToAdd.getPickupDate(), rentalToAdd.getReturnDate()));// tarih fark??
		rentalToAdd.setTotalPrice(calculateTotalPrice( rentalToAdd.getTotalDays(),carToGet ,rentalToAdd ));;
		corporateRentalRepository.save(rentalToAdd);
		changeCarState(rentalToAdd.getCar().getId());// ara?? eklendikten sonra durumu kiraland?? olarak de??i??ir
		return new SuccessResult("rental added successfully");
	}

	@Override
	public Result update(UpdateCorporateRentalRequest updateCorporateRentalRequest) {
		checkCar(updateCorporateRentalRequest.getCarId()); // g??ncellenecek ara?? kontorl??
		checkIfCustomersExistsById(updateCorporateRentalRequest.getCorporateCustomerId()); // g??ncellenecek m????teri kontorl??
		checkIfRentalsExistsById(updateCorporateRentalRequest.getId()); // g??ncelleme yapaca????m??z kiralama i??lemi kontrol??

		CorporateRental rentalToUpdate = this.modelmapperService.forRequest().map(updateCorporateRentalRequest, CorporateRental.class);
		Car carToGet = carRepository.findById(rentalToUpdate.getCar().getId()).get();
		rentalToUpdate.setTotalDays(diffDay(updateCorporateRentalRequest.getPickupDate(), updateCorporateRentalRequest.getReturnDate()));
		calculateTotalPrice( rentalToUpdate.getTotalDays(),carToGet ,rentalToUpdate );
		changeCarStateForUpdate(updateCorporateRentalRequest.getId());// ara?? de??i??irse ,eski ara?? durum de??i??tir
		corporateRentalRepository.save(rentalToUpdate);
		changeCarState(rentalToUpdate.getCar().getId());// yeni eklenen arac??n durumunu de??i??tir.
		return new SuccessResult("rental updated successfully");
	}

	@Override
	public Result delete(DeleteCorporateRentalRequest deleteCorporateRentalRequest) {
		checkIfRentalsExistsById(deleteCorporateRentalRequest.getId());
		corporateRentalRepository.deleteById(deleteCorporateRentalRequest.getId());
		return new SuccessResult("rental deleted successfully");
	}

	@Override
	public DataResult<CorporateRental> getById(GetCorporateRentalResponse getCorporateRentalResponse) {
		checkIfRentalsExistsById(getCorporateRentalResponse.getId());
		CorporateRental individualRental = this.modelmapperService.forResponce().map(getCorporateRentalResponse, CorporateRental.class);
		individualRental = corporateRentalRepository.findById(getCorporateRentalResponse.getId()).get();
		return new SuccessDataResult<CorporateRental>(individualRental, "rental listed successfully");
	}

	@Override
	public DataResult<List<GetAllCorporateRentalResponse>> getAll() {
		List<CorporateRental> individualRentals = corporateRentalRepository.findAll();
		List<GetAllCorporateRentalResponse> responce = individualRentals.stream()
				.map(item -> modelmapperService.forResponce().map(item, GetAllCorporateRentalResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllCorporateRentalResponse>>(responce, "individualRentals listed successfully");
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

	// Kiralama i??lemi daha ??nceden var m?? kontrol??n?? sa??lar
	private void checkIfRentalsExistsById(int id) {
		boolean result = corporateRentalRepository.existsById(id);
		if (result == false) {
			throw new BusinessException("IndividualRental NOT EXISTS");
		}
	}

	// G??ncellemelede ve eklemede olmayan m????teri kontrol?? sa??lar.
	private void checkIfCustomersExistsById(int id) {
		boolean result = corporateCustomerRepository.existsById(id);
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
		CorporateRental rentalForGetBeforeCarId = corporateRentalRepository.findById(id).get();
		Car carToUpdate = carRepository.findById(rentalForGetBeforeCarId.getCar().getId()).get();
		carToUpdate.setState("avaliable");
		carRepository.save(carToUpdate);
	}
	private double calculateTotalPrice(int totalDays, Car carToCalculate, CorporateRental rentalToCalculate) {
		double totalPrice = totalDays * carToCalculate.getDailyPrice();

		if (rentalToCalculate.getPickupCityId()!=(rentalToCalculate.getReturnCityId())) {
			totalPrice += 750;
		}
		return totalPrice;
	}
}
