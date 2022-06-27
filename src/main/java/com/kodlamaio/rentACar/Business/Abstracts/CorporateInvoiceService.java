package com.kodlamaio.rentACar.Business.Abstracts;

import java.util.List;

import com.kodlamaio.rentACar.Business.Requests.corporateInvoice.CreateCorporateInvoiceRequest;
import com.kodlamaio.rentACar.Business.Requests.corporateInvoice.StateUpdateCorporateInvoiceRequest;
import com.kodlamaio.rentACar.Business.Responces.corporateInvoice.GetAllCorporateInvoiceResponse;
import com.kodlamaio.rentACar.Business.Responces.corporateInvoice.GetCorporateInvoiceResponce;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.CorporateInvoice;

public interface CorporateInvoiceService 
{

	Result add(CreateCorporateInvoiceRequest createCorporateInvoiceRequest);
	Result cancelInvoice(StateUpdateCorporateInvoiceRequest stateUpdateCorporateInvoiceRequest);
	Result activateInvoice(StateUpdateCorporateInvoiceRequest stateUpdateCorporateInvoiceRequest);
	DataResult<List<GetAllCorporateInvoiceResponse>> getAll();
	DataResult<CorporateInvoice> getById(GetCorporateInvoiceResponce getCorporateInvoiceResponce);
}
