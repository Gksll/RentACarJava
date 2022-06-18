package com.kodlamaio.rentACar.Business.Abstracts;

import java.util.List;

import com.kodlamaio.rentACar.Business.Requests.rentalDetails.createRentalDetailRequest;
import com.kodlamaio.rentACar.Business.Requests.rentalDetails.deleteRentalDetailRequest;
import com.kodlamaio.rentACar.Business.Requests.rentalDetails.updateRentalDetailRequest;
import com.kodlamaio.rentACar.Business.Responces.rentalDetails.getAllRentalDetailResponse;
import com.kodlamaio.rentACar.Business.Responces.rentalDetails.getRentalDetailResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.RentalDetail;

public interface RentalDetailService {
	Result add(createRentalDetailRequest createRentalDetailRequest);

	Result update(updateRentalDetailRequest updateRentalDetailRequest);

	Result delete(deleteRentalDetailRequest deleteRentalDetailRequest);

	DataResult<RentalDetail> getById(getRentalDetailResponse getRentalDetailResponse);

	DataResult<List<getAllRentalDetailResponse>> getAll();
	
}
