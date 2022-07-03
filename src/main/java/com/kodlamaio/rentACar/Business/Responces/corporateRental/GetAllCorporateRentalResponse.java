package com.kodlamaio.rentACar.Business.Responces.corporateRental;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCorporateRentalResponse {
	private int id;
	private Date pickupDate;
	private Date returnDate;
	private int totalDays;
	private double totalPrice;
	private int carId;
	private int corporateCustomerId;
	private Integer pickupCityId;
	private Integer returnCityId;
}
