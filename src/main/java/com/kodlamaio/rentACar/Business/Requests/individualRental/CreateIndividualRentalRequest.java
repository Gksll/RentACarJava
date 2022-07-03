package com.kodlamaio.rentACar.Business.Requests.individualRental;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateIndividualRentalRequest {
	private Date pickupDate;
	private Date returnDate;
	private int totalDays;
	private double totalPrice;
	private Integer pickupCityId;
	private Integer returnCityId;
	private int carId;
	private int individualCustomerId;

}
