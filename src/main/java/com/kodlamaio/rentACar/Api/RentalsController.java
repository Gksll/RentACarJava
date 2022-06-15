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
import com.kodlamaio.rentACar.Core.Utilities.Results.ErrorResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
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
		var result = rentalService.add(createRentalRequest);
		if (result.isSuccess()) {
			return new SuccessResult("başarıyla kiralandı");
		} else {
			return new ErrorResult("eklenemedi sorun!");
		}
	}

	@PostMapping("/update")
	public Result update(@RequestBody UpdateRentalRequest updateRentalRequest) {
		rentalService.update(updateRentalRequest);
		return new SuccessResult();
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteRentalRequest deleteRentalRequest) {
		rentalService.delete(deleteRentalRequest);
		return new SuccessResult();
	}

	@GetMapping("/getbyid")
	public DataResult<Rental> getById(GetRentalResponse getRentalResponse) {

		return this.rentalService.getById(getRentalResponse);
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllRentalResponse>> getall() {
		return this.rentalService.getAll();
//		return new SuccessDataResult<List<Rental>>(rentalService.getAll().getData());
	}
}
