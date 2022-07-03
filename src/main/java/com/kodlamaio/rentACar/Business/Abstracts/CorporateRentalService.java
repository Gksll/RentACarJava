package com.kodlamaio.rentACar.Business.Abstracts;

import java.util.List;

import com.kodlamaio.rentACar.Business.Requests.corporateRental.CreateCorporateRentalRequest;
import com.kodlamaio.rentACar.Business.Requests.corporateRental.DeleteCorporateRentalRequest;
import com.kodlamaio.rentACar.Business.Requests.corporateRental.UpdateCorporateRentalRequest;
import com.kodlamaio.rentACar.Business.Responces.corporateRental.GetAllCorporateRentalResponse;
import com.kodlamaio.rentACar.Business.Responces.corporateRental.GetCorporateRentalResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.CorporateRental;



public interface CorporateRentalService {
	Result add(CreateCorporateRentalRequest createCorporateRentalRequest);

	Result update(UpdateCorporateRentalRequest updateCorporateRentalRequest);

	Result delete(DeleteCorporateRentalRequest deleteCorporateRentalRequest);

	DataResult<CorporateRental> getById(GetCorporateRentalResponse getCorporateRentalResponse);

	DataResult<List<GetAllCorporateRentalResponse>> getAll();

}
