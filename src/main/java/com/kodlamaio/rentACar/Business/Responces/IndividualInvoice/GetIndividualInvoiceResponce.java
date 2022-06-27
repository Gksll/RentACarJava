package com.kodlamaio.rentACar.Business.Responces.IndividualInvoice;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetIndividualInvoiceResponce {
	private int id;
	private int invoiceNumber;
	private double totalPrice;
	private boolean state;
	private int additionalServiceId;
	private int rentalId;
	private int individualCustomerId;
}
