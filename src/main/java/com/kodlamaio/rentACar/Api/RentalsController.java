package com.kodlamaio.rentACar.Api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.RentalService;
import com.kodlamaio.rentACar.Business.Requests.rental.CreateRentalRequest;
import com.kodlamaio.rentACar.Business.Requests.rental.DeleteRentalRequest;
import com.kodlamaio.rentACar.Business.Requests.rental.UpdateRentalRequest;
import com.kodlamaio.rentACar.Business.Responces.rental.GetAllRentalResponse;
import com.kodlamaio.rentACar.Business.Responces.rental.GetRentalResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.Rental;

@RestController
@RequestMapping("api/rentals")
public class RentalsController {

	private RentalService rentalService;

	public RentalsController(RentalService rentalService) {
		this.rentalService = rentalService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateRentalRequest createRentalRequest) {
		return rentalService.add(createRentalRequest);
	}

	@PostMapping("/update")
	public Result Update(@RequestBody UpdateRentalRequest updateRentalRequest) {
		return rentalService.update(updateRentalRequest);
	}

	@PostMapping("/delete")
	public Result Delete(DeleteRentalRequest deleteRentalRequest) {
		return rentalService.delete(deleteRentalRequest);
	}

	@GetMapping("/getById")
	public DataResult<Rental> GetById(GetRentalResponse getRentalResponse) {
		return rentalService.getById(getRentalResponse);
	}

	@GetMapping("/getAll")
	public DataResult<List<GetAllRentalResponse>> GetAll() {
		return this.rentalService.getAll();
	}
}
