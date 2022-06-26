package com.kodlamaio.rentACar.Api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.MaintenanceService;
import com.kodlamaio.rentACar.Business.Requests.maintenance.CreateMaintenanceRequest;
import com.kodlamaio.rentACar.Business.Requests.maintenance.DeleteMaintenanceRequest;
import com.kodlamaio.rentACar.Business.Requests.maintenance.UpdateMaintenanceRequest;
import com.kodlamaio.rentACar.Business.Responces.maintenance.GetAllMaintenanceResponse;
import com.kodlamaio.rentACar.Business.Responces.maintenance.GetMaintenanceResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.Maintenance;

@RestController
@RequestMapping("api/maintenances")
public class MaintenancesController {

	private MaintenanceService maintenanceService;

	public MaintenancesController(MaintenanceService maintenanceService) {
		this.maintenanceService = maintenanceService;
	}

	@PostMapping("/add")
	public Result Add(@RequestBody CreateMaintenanceRequest createMaintenanceRequest) {
		return maintenanceService.add(createMaintenanceRequest);
	}

	@PostMapping("/update")
	public Result Update(@RequestBody UpdateMaintenanceRequest updateMaintenanceRequest) {
		return maintenanceService.update(updateMaintenanceRequest);
	}

	@PostMapping("/delete")
	public Result Delete(DeleteMaintenanceRequest deleteMaintenanceRequest) {
		return maintenanceService.delete(deleteMaintenanceRequest);
	}

	@GetMapping("/getById")
	public DataResult<Maintenance> GetById(GetMaintenanceResponse getMaintenanceResponse) {
		return maintenanceService.getById(getMaintenanceResponse);
	}

	@GetMapping("/getAll")
	public DataResult<List<GetAllMaintenanceResponse>> GetAll() {
		return maintenanceService.getAll();
	}

}
