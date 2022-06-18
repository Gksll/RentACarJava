package com.kodlamaio.rentACar.Business.Concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.InvoiceService;
import com.kodlamaio.rentACar.Business.Requests.Invoice.CreateInvoiceRequest;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.DataAccess.Abstracts.InvoiceRepository;
import com.kodlamaio.rentACar.Entities.Concretes.Invoice;

@Service
public class InvoiceManager implements InvoiceService {
	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private ModelMapperService modelMapperService;

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
		Invoice invoice = modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		invoiceRepository.save(invoice);
		return new SuccessResult("Fatura eklendi");
	}

}
