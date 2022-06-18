package com.kodlamaio.rentACar.Business.Requests.Invoice;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateInvoiceRequest {

	private int id;
	private int invoiceNumber;
	private int rentalDetailId;
	
}
