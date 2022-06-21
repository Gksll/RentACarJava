package com.kodlamaio.rentACar.Business.Concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.RentalDetailService;
import com.kodlamaio.rentACar.Business.Requests.rentalDetails.createRentalDetailRequest;
import com.kodlamaio.rentACar.Business.Requests.rentalDetails.deleteRentalDetailRequest;
import com.kodlamaio.rentACar.Business.Requests.rentalDetails.updateRentalDetailRequest;
import com.kodlamaio.rentACar.Business.Responces.rentalDetails.getAllRentalDetailResponse;
import com.kodlamaio.rentACar.Business.Responces.rentalDetails.getRentalDetailResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessDataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.DataAccess.Abstracts.AdditionalServiceRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.RentalDetailRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.RentalRepository;
import com.kodlamaio.rentACar.Entities.Concretes.AdditionalService;
import com.kodlamaio.rentACar.Entities.Concretes.Rental;
import com.kodlamaio.rentACar.Entities.Concretes.RentalDetail;

@Service
public class RentalDetailManager implements RentalDetailService {

	@Autowired
	private RentalDetailRepository rentalDetailRepository;
	@Autowired
	private RentalRepository rentalRepository;
	@Autowired
	private AdditionalServiceRepository additionalServiceRepository;
	@Autowired
	private ModelMapperService mapperService;

	@Override
	public Result add(createRentalDetailRequest createRentalDetailRequest) {
		RentalDetail rentalDetail = mapperService.forRequest().map(createRentalDetailRequest, RentalDetail.class);
		Rental rental = rentalRepository.findById(rentalDetail.getRental().getId()).get();
		AdditionalService additionalService = additionalServiceRepository
				.findById(rentalDetail.getAdditionalService().getId()).get();
		rentalDetail.setTotalPrice(rental.getTotalPrice() + additionalService.getTotalPrice());
		rentalDetailRepository.save(rentalDetail);
		return new SuccessResult("Kira detay eklendi");
	}

	@Override
	public Result update(updateRentalDetailRequest updateRentalDetailRequest) {
		RentalDetail rentalDetailToUpdate = mapperService.forRequest().map(updateRentalDetailRequest,
				RentalDetail.class);
		rentalDetailRepository.save(rentalDetailToUpdate);
		return new SuccessResult("Kira detay güncellendi");
	}

	@Override
	public Result delete(deleteRentalDetailRequest deleteRentalDetailRequest) {
		RentalDetail rentalDetail = mapperService.forRequest().map(deleteRentalDetailRequest, RentalDetail.class);
		rentalDetailRepository.delete(rentalDetail);
		return new SuccessResult("Kira detay silindi");
	}

	@Override
	public DataResult<RentalDetail> getById(getRentalDetailResponse getRentalDetailResponse) {
		RentalDetail rentalDetail = this.mapperService.forResponce().map(getRentalDetailResponse, RentalDetail.class);
		rentalDetail = rentalDetailRepository.findById(getRentalDetailResponse.getId()).get();
		return new SuccessDataResult<RentalDetail>(rentalDetail);
	}

	@Override
	public DataResult<List<getAllRentalDetailResponse>> getAll() {
		List<RentalDetail> rentalDetails = this.rentalDetailRepository.findAll();
		List<getAllRentalDetailResponse> responce = rentalDetails.stream()
				.map(item -> this.mapperService.forResponce().map(item, getAllRentalDetailResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<getAllRentalDetailResponse>>(responce);
	}

}
