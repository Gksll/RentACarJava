package com.kodlamaio.rentACar.Business.Requests.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerRequest {
	private int id;
	private String firstName;
	private String lastName;
	private String tcNo;
	private String email;
	private Integer birthYear;
	private String password;
	private int addressId;
}
