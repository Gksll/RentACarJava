package com.kodlamaio.rentACar.Business.Requests.address;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAddressRequest {
	private String address;
	private String invoiceAdress;
	
}
