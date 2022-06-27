package com.kodlamaio.rentACar.Api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.IndividualInvoiceService;
import com.kodlamaio.rentACar.Business.Requests.IndividualInvoice.CreateIndividualInvoiceRequest;
import com.kodlamaio.rentACar.Business.Requests.IndividualInvoice.StateUpdateIndividualInvoiceRequest;
import com.kodlamaio.rentACar.Business.Responces.IndividualInvoice.GetAllIndividualInvoiceResponse;
import com.kodlamaio.rentACar.Business.Responces.IndividualInvoice.GetIndividualInvoiceResponce;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.IndividualInvoice;

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
	@PostMapping("/activate")
	public Result Activate(@RequestBody StateUpdateIndividualInvoiceRequest stateUpdateIndividualInvoiceRequest) {
		return individualInvoiceService.activateInvoice(stateUpdateIndividualInvoiceRequest);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<GetAllIndividualInvoiceResponse>> GetAll() {
		return individualInvoiceService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<IndividualInvoice> GetById(GetIndividualInvoiceResponce getIndividualInvoiceResponce) {
		return individualInvoiceService.getById(getIndividualInvoiceResponce);
	}
}
