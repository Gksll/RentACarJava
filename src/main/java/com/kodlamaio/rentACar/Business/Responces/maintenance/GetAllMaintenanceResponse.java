package com.kodlamaio.rentACar.Business.Responces.maintenance;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllMaintenanceResponse {
	private int id;	
	private Date sendDate;
	private Date returnDate;
	private int carId;
}
