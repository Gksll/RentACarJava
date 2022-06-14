package com.kodlamaio.rentACar.Business.Requests.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
	private int id;
	private String firstName;
	private String lastName;
	private String tcNo;
	private String email;
	private Integer birthYear;
	private String password;
}
