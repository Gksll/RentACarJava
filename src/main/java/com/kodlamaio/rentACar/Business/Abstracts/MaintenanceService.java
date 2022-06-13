package com.kodlamaio.rentACar.Business.Abstracts;

import java.util.List;

import com.kodlamaio.rentACar.Business.Requests.maintenance.CreateMaintenanceRequest;
import com.kodlamaio.rentACar.Business.Requests.maintenance.DeleteMaintenanceRequest;
import com.kodlamaio.rentACar.Business.Requests.maintenance.UpdateMaintenanceRequest;
import com.kodlamaio.rentACar.Business.Responces.maintenance.GetAllMaintenanceResponse;
import com.kodlamaio.rentACar.Business.Responces.maintenance.GetMaintenanceResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.Maintenance;

public interface MaintenanceService {
	Result add(CreateMaintenanceRequest createMaintenanceRequest);

	Result delete(DeleteMaintenanceRequest deleteMaintenanceRequest);

	Result update(UpdateMaintenanceRequest updateMaintenanceRequest);

	DataResult<Maintenance> getById(GetMaintenanceResponse maintenanceResponse);

	DataResult<List<GetAllMaintenanceResponse>> getAll();
}
