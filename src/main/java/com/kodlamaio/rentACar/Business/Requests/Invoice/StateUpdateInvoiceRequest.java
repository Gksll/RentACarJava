package com.kodlamaio.rentACar.Business.Requests.Invoice;


import com.kodlamaio.rentACar.Entities.Concretes.RentalDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateUpdateInvoiceRequest {
	private int id;
	private int invoiceNumber;
	private boolean state;
	private RentalDetail rentalDetail;

	
}
