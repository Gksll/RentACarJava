package com.kodlamaio.rentACar.Business.Responces.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCustomerResponse {
private int id;
private String firstName;
private String lastName;
private String tcNo;
private String email;
private String password;
private Integer birthYear;
private int addressId;
}
