package com.kodlamaio.rentACar.Business.Requests.rental;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRentalRequest {
	private int id;
	private Date pickupDate;
	private Date returnDate;
	private double totalDays;
	private double totalPrice;
	private int carId;
	private int additionalServiceId;
	private int userId;
	
}
