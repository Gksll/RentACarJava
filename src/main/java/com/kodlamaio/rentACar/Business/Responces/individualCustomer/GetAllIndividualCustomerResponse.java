package com.kodlamaio.rentACar.Business.Responces.individualCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllIndividualCustomerResponse {
private int id;
private String firstName;
private String lastName;
private String identityNumber;
private String email;
private String password;
private Integer birthYear;
private int addressId;
}
