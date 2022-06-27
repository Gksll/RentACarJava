package com.kodlamaio.rentACar.Business.Abstracts;

import com.kodlamaio.rentACar.Business.Requests.corporateInvoice.CreateCorporateInvoiceRequest;
import com.kodlamaio.rentACar.Business.Requests.corporateInvoice.StateUpdateCorporateInvoiceRequest;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;

public interface CorporateInvoiceService 
{

	Result add(CreateCorporateInvoiceRequest createCorporateInvoiceRequest);
	Result cancelInvoice(StateUpdateCorporateInvoiceRequest stateUpdateCorporateInvoiceRequest);
}
