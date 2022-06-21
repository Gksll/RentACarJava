package com.kodlamaio.rentACar.Business.Abstracts;

import com.kodlamaio.rentACar.Business.Requests.Invoice.CreateInvoiceRequest;
import com.kodlamaio.rentACar.Business.Requests.Invoice.StateUpdateInvoiceRequest;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;

public interface InvoiceService 
{

	Result add(CreateInvoiceRequest createInvoiceRequest);
	boolean checkIfInvoicesNumber(int invoiceNumber);
	Result cancelInvoice(StateUpdateInvoiceRequest stateUpdateInvoiceRequest);
}
