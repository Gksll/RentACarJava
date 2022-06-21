package com.kodlamaio.rentACar.Business.Responces.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllAddressResponse {
	private int id;
	private String address;
	private String invoiceAdress;
}
