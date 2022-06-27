package com.kodlamaio.rentACar.Business.Abstracts;

import com.kodlamaio.rentACar.Business.Requests.IndividualInvoice.CreateIndividualInvoiceRequest;
import com.kodlamaio.rentACar.Business.Requests.IndividualInvoice.StateUpdateIndividualInvoiceRequest;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;

public interface IndividualInvoiceService 
{

	Result add(CreateIndividualInvoiceRequest createIndividualInvoiceRequest);
	Result cancelInvoice(StateUpdateIndividualInvoiceRequest stateUpdateIndividualInvoiceRequest);
}
