package com.kodlamaio.rentACar.Api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.IndividualRentalService;
import com.kodlamaio.rentACar.Business.Requests.individualRental.CreateIndividualRentalRequest;
import com.kodlamaio.rentACar.Business.Requests.individualRental.DeleteIndividualRentalRequest;
import com.kodlamaio.rentACar.Business.Requests.individualRental.UpdateIndividualRentalRequest;
import com.kodlamaio.rentACar.Business.Responces.individualRental.GetAllIndividualRentalResponse;
import com.kodlamaio.rentACar.Business.Responces.individualRental.GetIndividualRentalResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.IndividualRental;

@RestController
@RequestMapping("api/individualRentals")
public class IndividualRentalsController {

	private IndividualRentalService individualRentalService;

	public IndividualRentalsController(IndividualRentalService individualRentalService) {
		this.individualRentalService = individualRentalService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateIndividualRentalRequest createIndividualRentalRequest) {
		return individualRentalService.add(createIndividualRentalRequest);
	}

	@PostMapping("/update")
	public Result Update(@RequestBody UpdateIndividualRentalRequest updateIndividualRentalRequest) {
		return individualRentalService.update(updateIndividualRentalRequest);
	}

	@PostMapping("/delete")
	public Result Delete(DeleteIndividualRentalRequest deleteIndividualRentalRequest) {
		return individualRentalService.delete(deleteIndividualRentalRequest);
	}

	@GetMapping("/getById")
	public DataResult<IndividualRental> GetById(GetIndividualRentalResponse getIndividualRentalResponse) {
		return individualRentalService.getById(getIndividualRentalResponse);
	}

	@GetMapping("/getAll")
	public DataResult<List<GetAllIndividualRentalResponse>> GetAll() {
		return this.individualRentalService.getAll();
	}
}
