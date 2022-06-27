package com.kodlamaio.rentACar.Api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.IndividualInvoiceService;
import com.kodlamaio.rentACar.Business.Requests.IndividualInvoice.CreateIndividualInvoiceRequest;
import com.kodlamaio.rentACar.Business.Requests.IndividualInvoice.StateUpdateIndividualInvoiceRequest;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;

@RestController
@RequestMapping("api/individualInvoices")
public class IndividualInvoicesController {

	private IndividualInvoiceService individualInvoiceService;

	public IndividualInvoicesController(IndividualInvoiceService individualInvoiceService) {
		this.individualInvoiceService = individualInvoiceService;
	}

	@PostMapping("/add")
	public Result Add(@RequestBody CreateIndividualInvoiceRequest createIndividualInvoiceRequest) {
		return individualInvoiceService.add(createIndividualInvoiceRequest);
	}
	
	@PostMapping("/cancel")
	public Result Cancel(@RequestBody StateUpdateIndividualInvoiceRequest stateUpdateIndividualInvoiceRequest) {
		return individualInvoiceService.cancelInvoice(stateUpdateIndividualInvoiceRequest);
	}
}
