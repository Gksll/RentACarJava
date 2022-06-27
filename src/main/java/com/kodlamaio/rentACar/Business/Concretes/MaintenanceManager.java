package com.kodlamaio.rentACar.Business.Concretes;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.MaintenanceService;
import com.kodlamaio.rentACar.Business.Requests.maintenance.CreateMaintenanceRequest;
import com.kodlamaio.rentACar.Business.Requests.maintenance.DeleteMaintenanceRequest;
import com.kodlamaio.rentACar.Business.Requests.maintenance.UpdateMaintenanceRequest;
import com.kodlamaio.rentACar.Business.Responces.maintenance.GetAllMaintenanceResponse;
import com.kodlamaio.rentACar.Business.Responces.maintenance.GetMaintenanceResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessDataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.DataAccess.Abstracts.CarRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.MaintenanceRepository;
import com.kodlamaio.rentACar.Entities.Concretes.Car;
import com.kodlamaio.rentACar.Entities.Concretes.Maintenance;

@Service
public class MaintenanceManager implements MaintenanceService {
	@Autowired
	private MaintenanceRepository maintenanceRepository;
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private ModelMapperService modelmapperService;

	@Override
	public Result add(CreateMaintenanceRequest createMaintenanceRequest) {
		checkCar(createMaintenanceRequest.getCarId());
		checkCarMaintenancesState(createMaintenanceRequest.getCarId());
		diffDay(createMaintenanceRequest.getSendDate(),createMaintenanceRequest.getReturnDate());
		Maintenance maintenance = this.modelmapperService.forRequest().map(createMaintenanceRequest, Maintenance.class);
		maintenanceRepository.save(maintenance);
		changeCarState(maintenance.getCar().getId());
		return new SuccessResult("maintenance added successfully");
	}

	@Override
	public Result delete(DeleteMaintenanceRequest deleteMaintenanceRequest) {
		checkIfMaintenancesExistsById(deleteMaintenanceRequest.getId());
		maintenanceRepository.deleteById(deleteMaintenanceRequest.getId());
		return new SuccessResult("maintenance deleted successfully");
	}

	@Override
	public Result update(UpdateMaintenanceRequest updateMaintenanceRequest) {
		checkIfMaintenancesExistsById(updateMaintenanceRequest.getId());
		diffDay(updateMaintenanceRequest.getSendDate(),updateMaintenanceRequest.getReturnDate());
		Maintenance maintenance = this.modelmapperService.forRequest().map(updateMaintenanceRequest, Maintenance.class);
		maintenanceRepository.save(maintenance);
		return new SuccessResult("maintenance updated successfully");
	}

	@Override
	public DataResult<Maintenance> getById(GetMaintenanceResponse maintenanceResponse) {
		checkIfMaintenancesExistsById(maintenanceResponse.getId());
		Maintenance maintenanceToGet = this.modelmapperService.forResponce().map(maintenanceResponse,
				Maintenance.class);
		maintenanceToGet = this.maintenanceRepository.findById(maintenanceResponse.getId()).get();
		return new SuccessDataResult<Maintenance>(maintenanceToGet, "maintenance listed successfully");
	}

	@Override
	public DataResult<List<GetAllMaintenanceResponse>> getAll() {
		List<Maintenance> maintenances = this.maintenanceRepository.findAll();
		List<GetAllMaintenanceResponse> responce = maintenances.stream().map(
				maintenance -> this.modelmapperService.forResponce().map(maintenance, GetAllMaintenanceResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllMaintenanceResponse>>(responce, "maintenances listed successfully");
	}

	// Bakım eklendiğinde aracın state bilgisini günceller.
	private void changeCarState(int id) {
		Car carToUpdate = carRepository.findById(id).get();
		carToUpdate.setState("maintenance");
		carRepository.save(carToUpdate);
	}

	// Aracı eklemeden state bilgisini kontrol eder.
	private void checkCarMaintenancesState(int carId) {
		Car carToCheck = carRepository.findById(carId).get();
		if (!carToCheck.getState().equals("avaliable")) {
			throw new BusinessException("THE CAR NOT READY FOR UNDERMAINTENANCES");
		}
	}
	// olmayan bakımlar için işlem (delete,update,getbyid) olmaması adına kontrol
	// sağlar.
	private void checkIfMaintenancesExistsById(int id) {
		boolean result = maintenanceRepository.existsById(id);
		if (result == false) {
			throw new BusinessException("MAINTENANCE NOT EXISTS");
		}
	}
	// var olmayan araç bakıma gönderilemez, kontrolü sağlandı.
	private void checkCar(int carId) {
		boolean result = carRepository.existsById(carId);
		if (result == false) {
			throw new BusinessException("THE CAR DOESNT EXIST");
		}
	}
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
}
