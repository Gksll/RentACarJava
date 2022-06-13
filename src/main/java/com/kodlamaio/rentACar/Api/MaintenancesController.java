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
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Entities.Concretes.Maintenance;

@RestController
@RequestMapping("api/maintenances")
public class MaintenancesController {

	private MaintenanceService maintenanceService;

	public MaintenancesController(MaintenanceService maintenanceService) {
		this.maintenanceService = maintenanceService;
	}
	
	@PostMapping("/add")
	public Result add( @RequestBody CreateMaintenanceRequest createMaintenanceRequest) {
		maintenanceService.add(createMaintenanceRequest);
		return new SuccessResult();
	}
	@PostMapping("/update")
	public Result update( @RequestBody UpdateMaintenanceRequest updateMaintenanceRequest) {
		maintenanceService.update(updateMaintenanceRequest);
		return new SuccessResult();
	}
	@PostMapping("/delete")
	public Result delete( @RequestBody DeleteMaintenanceRequest deleteMaintenanceRequest) {
		maintenanceService.delete(deleteMaintenanceRequest);
		return new SuccessResult();
	}
	@GetMapping("/getbyid")
	public DataResult<Maintenance> getById( GetMaintenanceResponse maintenanceResponse) {
		
		return this.maintenanceService.getById(maintenanceResponse);   
		
	}
	@GetMapping("/getall")
	public DataResult<List<GetAllMaintenanceResponse>> getAll( ) {
		return this.maintenanceService.getAll();
	}
	
	
}
