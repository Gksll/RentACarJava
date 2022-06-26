package com.kodlamaio.rentACar.Api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.InvoiceService;
import com.kodlamaio.rentACar.Business.Requests.Invoice.CreateInvoiceRequest;
import com.kodlamaio.rentACar.Business.Requests.Invoice.StateUpdateInvoiceRequest;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;

@RestController
@RequestMapping("api/invoices")
public class InvoicesController {

	private InvoiceService invoiceService;

	public InvoicesController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@PostMapping("/add")
	public Result Add(@RequestBody CreateInvoiceRequest createInvoiceRequest) {
		return invoiceService.add(createInvoiceRequest);
	}
	
	@PostMapping("/cancel")
	public Result Cancel(@RequestBody StateUpdateInvoiceRequest stateUpdateInvoiceRequest) {
		return invoiceService.cancelInvoice(stateUpdateInvoiceRequest);
	}
}
