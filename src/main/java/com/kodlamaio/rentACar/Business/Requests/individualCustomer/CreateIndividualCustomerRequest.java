package com.kodlamaio.rentACar.Business.Requests.individualCustomer;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateIndividualCustomerRequest {
	@Size(min = 2)
	private String firstName;
	@Size(min = 2)
	private String lastName;
	private Integer birthYear;
	private String identityNumber;
	private String email;
	private String password;
	private Integer addressId;
}
