package com.kodlamaio.rentACar.Business.Responces.corporateInvoice;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCorporateInvoiceResponse {
	private int id;
	private int invoiceNumber;
	private boolean state;

	
}
