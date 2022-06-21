package com.kodlamaio.rentACar.Business.Requests.rentalDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class createRentalDetailRequest {
	private int rentalId;
	private int additionalServiceId;
	private int customerId;

}
