package com.kodlamaio.rentACar.Api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.CorporateInvoiceService;
import com.kodlamaio.rentACar.Business.Requests.corporateInvoice.CreateCorporateInvoiceRequest;
import com.kodlamaio.rentACar.Business.Requests.corporateInvoice.StateUpdateCorporateInvoiceRequest;
import com.kodlamaio.rentACar.Business.Responces.corporateInvoice.GetAllCorporateInvoiceResponse;
import com.kodlamaio.rentACar.Business.Responces.corporateInvoice.GetCorporateInvoiceResponce;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.CorporateInvoice;

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
	@PostMapping("/activate")
	public Result Activate(@RequestBody StateUpdateCorporateInvoiceRequest stateUpdateCorporateInvoiceRequest) {
		return corporateInvoiceService.activateInvoice(stateUpdateCorporateInvoiceRequest);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<GetAllCorporateInvoiceResponse>> GetAll() {
		return corporateInvoiceService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<CorporateInvoice> GetById(GetCorporateInvoiceResponce getCorporateInvoiceResponce) {
		return corporateInvoiceService.getById(getCorporateInvoiceResponce);
	}
}
