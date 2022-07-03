package com.kodlamaio.rentACar.Business.Abstracts;

import java.util.List;

import com.kodlamaio.rentACar.Business.Requests.individualRental.CreateIndividualRentalRequest;
import com.kodlamaio.rentACar.Business.Requests.individualRental.DeleteIndividualRentalRequest;
import com.kodlamaio.rentACar.Business.Requests.individualRental.UpdateIndividualRentalRequest;
import com.kodlamaio.rentACar.Business.Responces.individualRental.GetAllIndividualRentalResponse;
import com.kodlamaio.rentACar.Business.Responces.individualRental.GetIndividualRentalResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.IndividualRental;

public interface IndividualRentalService {
	Result add(CreateIndividualRentalRequest createIndividualRentalRequest);

	Result update(UpdateIndividualRentalRequest updateIndividualRentalRequest);

	Result delete(DeleteIndividualRentalRequest deleteIndividualRentalRequest);

	DataResult<IndividualRental> getById(GetIndividualRentalResponse getIndividualRentalResponse);

	DataResult<List<GetAllIndividualRentalResponse>> getAll();

}
