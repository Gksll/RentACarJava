package com.kodlamaio.rentACar.Business.Abstracts;

import java.util.List;

import com.kodlamaio.rentACar.Business.Requests.IndividualInvoice.CreateIndividualInvoiceRequest;
import com.kodlamaio.rentACar.Business.Requests.IndividualInvoice.StateUpdateIndividualInvoiceRequest;
import com.kodlamaio.rentACar.Business.Responces.IndividualInvoice.GetAllIndividualInvoiceResponse;
import com.kodlamaio.rentACar.Business.Responces.IndividualInvoice.GetIndividualInvoiceResponce;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.IndividualInvoice;

public interface IndividualInvoiceService 
{

	Result add(CreateIndividualInvoiceRequest createIndividualInvoiceRequest);
	Result cancelInvoice(StateUpdateIndividualInvoiceRequest stateUpdateIndividualInvoiceRequest);
	Result activateInvoice(StateUpdateIndividualInvoiceRequest stateUpdateIndividualInvoiceRequest);
	DataResult<List<GetAllIndividualInvoiceResponse>> getAll();
	DataResult<IndividualInvoice> getById(GetIndividualInvoiceResponce getIndividualInvoiceResponce);
}
