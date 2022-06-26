package com.kodlamaio.rentACar.Api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.RentalDetailService;
import com.kodlamaio.rentACar.Business.Requests.rentalDetails.createRentalDetailRequest;
import com.kodlamaio.rentACar.Business.Requests.rentalDetails.deleteRentalDetailRequest;
import com.kodlamaio.rentACar.Business.Requests.rentalDetails.updateRentalDetailRequest;
import com.kodlamaio.rentACar.Business.Responces.rentalDetails.getAllRentalDetailResponse;
import com.kodlamaio.rentACar.Business.Responces.rentalDetails.getRentalDetailResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.RentalDetail;

@RestController
@RequestMapping("api/rentaldetails")
public class RentalDetailsController {

	private RentalDetailService rentalDetailService;

	public RentalDetailsController(RentalDetailService rentalDetailService) {
		this.rentalDetailService = rentalDetailService;
	}

	@PostMapping("/add")
	public Result Add(@RequestBody createRentalDetailRequest createRentalDetailRequest) {
		return rentalDetailService.add(createRentalDetailRequest);
	}

	@PostMapping("/update")
	public Result Update(@RequestBody updateRentalDetailRequest updateRentalDetailRequest) {
		return rentalDetailService.update(updateRentalDetailRequest);
		
	}

	@PostMapping("/delete")
	public Result Delete(deleteRentalDetailRequest deleteRentalDetailRequest) {
		return rentalDetailService.delete(deleteRentalDetailRequest);

	}

	@GetMapping("/getById")
	public DataResult<RentalDetail> GetById(getRentalDetailResponse getRentalDetailResponse) {

		return rentalDetailService.getById(getRentalDetailResponse);
	}

	@GetMapping("/getAll")
	public DataResult<List<getAllRentalDetailResponse>> GetAll() {
		return rentalDetailService.getAll();

	}
}
