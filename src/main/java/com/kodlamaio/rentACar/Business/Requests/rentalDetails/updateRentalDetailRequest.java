package com.kodlamaio.rentACar.Business.Requests.rentalDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class updateRentalDetailRequest {
	private int id;
	private double totalPrice;
	private int rentalId;
	private int additionalServiceId;
}
