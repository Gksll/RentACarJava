package com.kodlamaio.rentACar.Business.Requests.customer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest {
	@Size(min = 2)
	private String firstName;
	@Size(min = 2)
	private String lastName;
	@NotNull
	private Integer birthYear;
	@NotNull
	private String tcNo;
	@NotNull
	private String email;
	@NotNull
	private String password;
//	@Size(min = 10)
	private Integer addressId;
}
