package com.kodlamaio.rentACar.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.kodlamaio.rentACar.Core.Utilities.Results.ErrorResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Entities.Concretes.RentalDetail;

@RestController
@RequestMapping("api/rentaldetails")
public class RentalDetailsController {
@Autowired
	private RentalDetailService rentalDetailService;

	@PostMapping("/add")
	public Result add(@RequestBody createRentalDetailRequest createRentalRequest) {
		var result = rentalDetailService.add(createRentalRequest);
		if (result.isSuccess()) {
			return new SuccessResult(result.getMessage());
		} else {
			return new ErrorResult(result.getMessage());
		}
	}

	@PostMapping("/update")
	public Result update(@RequestBody updateRentalDetailRequest updateRentalRequest) {
		rentalDetailService.update(updateRentalRequest);
		return new SuccessResult();
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody deleteRentalDetailRequest deleteRentalRequest) {
		rentalDetailService.delete(deleteRentalRequest);
		return new SuccessResult();
	}

	@GetMapping("/getbyid")
	public DataResult<RentalDetail> getById(getRentalDetailResponse getRentalResponse) {

		return this.rentalDetailService.getById(getRentalResponse);
	}

	@GetMapping("/getall")
	public DataResult<List<getAllRentalDetailResponse>> getall() {
		return this.rentalDetailService.getAll();

	}
}
