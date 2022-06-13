package com.kodlamaio.rentACar.Business.Concretes;

import java.util.List;
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
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.DataAccess.Abstracts.CarRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.MaintenanceRepository;
import com.kodlamaio.rentACar.Entities.Concretes.Car;
import com.kodlamaio.rentACar.Entities.Concretes.Maintenance;

@Service
public class MaintenanceManager implements MaintenanceService {
	private MaintenanceRepository maintenanceRepository;
	private CarRepository carRepository;
	private ModelMapperService modelmapperService;

	@Autowired
	public MaintenanceManager(MaintenanceRepository maintenanceRepository, CarRepository carRepository,ModelMapperService modelmapperService) {
		this.maintenanceRepository = maintenanceRepository;
		this.carRepository = carRepository;
		this.modelmapperService=modelmapperService;
	}

	@Override
	public Result add(CreateMaintenanceRequest createMaintenanceRequest) {
		Maintenance maintenance = this.modelmapperService.forRequest().map(createMaintenanceRequest, Maintenance.class);
		Car carToUpdate = new Car();
		carToUpdate = carRepository.findById(createMaintenanceRequest.getCarId()).get();
		carToUpdate.setState("maintenance");
		maintenanceRepository.save(maintenance);
		carRepository.save(carToUpdate);
		return new SuccessResult("Bakım başarıyla eklenmiştir");
	}

	@Override
	public Result delete(DeleteMaintenanceRequest deleteMaintenanceRequest) {
		maintenanceRepository.deleteById(deleteMaintenanceRequest.getId());
		return new SuccessResult();
	}

	@Override
	public Result update(UpdateMaintenanceRequest updateMaintenanceRequest) {
		Maintenance maintenance = this.modelmapperService.forRequest().map(updateMaintenanceRequest, Maintenance.class);
		maintenanceRepository.save(maintenance);
		return new SuccessResult();
	}

	@Override
	public DataResult<Maintenance> getById(GetMaintenanceResponse maintenanceResponse) {
		Maintenance maintenanceToGet = this.modelmapperService.forResponce().map(maintenanceResponse, Maintenance.class);
		maintenanceToGet = this.maintenanceRepository.findById(maintenanceResponse.getId()).get();
		return new SuccessDataResult<Maintenance>(maintenanceToGet);
	}

	@Override
	public DataResult<List<GetAllMaintenanceResponse>> getAll() {
		List<Maintenance> maintenances = this.maintenanceRepository.findAll();
		List<GetAllMaintenanceResponse> responce = maintenances.stream()
				.map(maintenance -> this.modelmapperService.forResponce().map(maintenance, GetAllMaintenanceResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllMaintenanceResponse>>(responce);
	}

	


}
