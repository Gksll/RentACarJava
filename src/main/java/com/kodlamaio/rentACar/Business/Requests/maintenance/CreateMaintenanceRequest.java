package com.kodlamaio.rentACar.Business.Requests.maintenance;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateMaintenanceRequest {
	private Date sendDate;
	private Date returnDate;
	private int carId;
}
