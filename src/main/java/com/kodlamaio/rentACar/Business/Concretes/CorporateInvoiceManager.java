package com.kodlamaio.rentACar.Business.Concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.CorporateInvoiceService;
import com.kodlamaio.rentACar.Business.Requests.corporateInvoice.CreateCorporateInvoiceRequest;
import com.kodlamaio.rentACar.Business.Requests.corporateInvoice.StateUpdateCorporateInvoiceRequest;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.DataAccess.Abstracts.CorporateInvoiceRepository;

@Service
public class CorporateInvoiceManager implements CorporateInvoiceService {
	@Autowired
	private CorporateInvoiceRepository corporateInvoiceRepository;
	@Autowired
	private ModelMapperService modelMapperService;
	@Override
	public Result add(CreateCorporateInvoiceRequest createCorporateInvoiceRequest) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Result cancelInvoice(StateUpdateCorporateInvoiceRequest stateUpdateCorporateInvoiceRequest) {
		// TODO Auto-generated method stub
		return null;
	}
	

	

}
