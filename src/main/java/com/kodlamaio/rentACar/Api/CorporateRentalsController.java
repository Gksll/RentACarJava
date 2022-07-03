package com.kodlamaio.rentACar.Api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.CorporateRentalService;
import com.kodlamaio.rentACar.Business.Requests.corporateRental.CreateCorporateRentalRequest;
import com.kodlamaio.rentACar.Business.Requests.corporateRental.DeleteCorporateRentalRequest;
import com.kodlamaio.rentACar.Business.Requests.corporateRental.UpdateCorporateRentalRequest;
import com.kodlamaio.rentACar.Business.Responces.corporateRental.GetAllCorporateRentalResponse;
import com.kodlamaio.rentACar.Business.Responces.corporateRental.GetCorporateRentalResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.CorporateRental;

@RestController
@RequestMapping("api/corporateRentals")
public class CorporateRentalsController {

	private CorporateRentalService corporateRentalService;

	public CorporateRentalsController(CorporateRentalService corporateRentalService) {
		this.corporateRentalService = corporateRentalService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateCorporateRentalRequest createCorporateRentalRequest) {
		return corporateRentalService.add(createCorporateRentalRequest);
	}

	@PostMapping("/update")
	public Result Update(@RequestBody UpdateCorporateRentalRequest updateCorporateRentalRequest) {
		return corporateRentalService.update(updateCorporateRentalRequest);
	}

	@PostMapping("/delete")
	public Result Delete(DeleteCorporateRentalRequest deleteCorporateRentalRequest) {
		return corporateRentalService.delete(deleteCorporateRentalRequest);
	}

	@GetMapping("/getById")
	public DataResult<CorporateRental> GetById(GetCorporateRentalResponse getCorporateRentalResponse) {
		return corporateRentalService.getById(getCorporateRentalResponse);
	}

	@GetMapping("/getAll")
	public DataResult<List<GetAllCorporateRentalResponse>> GetAll() {
		return this.corporateRentalService.getAll();
	}
}
