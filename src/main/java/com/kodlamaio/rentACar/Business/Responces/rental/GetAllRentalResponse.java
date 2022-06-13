package com.kodlamaio.rentACar.Business.Responces.rental;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllRentalResponse {
	private int id;
	private Date pickUpdate;
	private Date returnDate;
	private double totalDays;
	private double totalPrice;
	private int carId;
	private int additionalServiceId;
}
