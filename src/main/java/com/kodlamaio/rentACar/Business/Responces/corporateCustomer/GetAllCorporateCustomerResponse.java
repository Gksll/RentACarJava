package com.kodlamaio.rentACar.Business.Responces.corporateCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCorporateCustomerResponse {
	private int id;
	private String companyName;
	private String email;
	private String password;
	private Integer addressId;
}
