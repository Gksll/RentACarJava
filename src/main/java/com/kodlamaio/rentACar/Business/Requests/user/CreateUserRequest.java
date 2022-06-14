package com.kodlamaio.rentACar.Business.Requests.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
	
	private String firstName;

	private String lastName;
	private Integer birthYear;
	@Min(value = 11)
	private String tcNo;
	@Email
	private String email;
	private String password;
}
