package com.kodlamaio.rentACar.Api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.CorporateInvoiceService;
import com.kodlamaio.rentACar.Business.Requests.corporateInvoice.CreateCorporateInvoiceRequest;
import com.kodlamaio.rentACar.Business.Requests.corporateInvoice.StateUpdateCorporateInvoiceRequest;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;

@RestController
@RequestMapping("api/corporateInvoices")
public class CorporateInvoicesController {

	private CorporateInvoiceService corporateInvoiceService;

	public CorporateInvoicesController(CorporateInvoiceService corporateInvoiceService) {
		this.corporateInvoiceService = corporateInvoiceService;
	}

	@PostMapping("/add")
	public Result Add(@RequestBody CreateCorporateInvoiceRequest createCorporateInvoiceRequest) {
		return corporateInvoiceService.add(createCorporateInvoiceRequest);
	}
	
	@PostMapping("/cancel")
	public Result Cancel(@RequestBody StateUpdateCorporateInvoiceRequest stateUpdateCorporateInvoiceRequest) {
		return corporateInvoiceService.cancelInvoice(stateUpdateCorporateInvoiceRequest);
	}
}
