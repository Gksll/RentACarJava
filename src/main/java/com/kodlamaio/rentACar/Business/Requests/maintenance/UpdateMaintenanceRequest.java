package com.kodlamaio.rentACar.Business.Requests.maintenance;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMaintenanceRequest {
	private int id;
	private Date sendDate;
	private Date returnDate;
	private int carId;
	
}
