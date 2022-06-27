package com.kodlamaio.rentACar.Business.Requests.IndividualInvoice;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateUpdateIndividualInvoiceRequest {
	private int id;
	private int invoiceNumber;
	private boolean state;

	
}
