package com.kodlamaio.rentACar.Business.Requests.corporateCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCorporateCustomerRequest {
	private int id;
	private String companyName;
	private String email;
	private String password;
	private Integer addressId;
	
}
