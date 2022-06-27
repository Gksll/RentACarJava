package com.kodlamaio.rentACar.Business.Responces.IndividualInvoice;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllIndividualInvoiceResponse {
	private int id;
	private int invoiceNumber;
	private boolean state;

	
}
