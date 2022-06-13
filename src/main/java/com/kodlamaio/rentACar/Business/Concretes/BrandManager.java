package com.kodlamaio.rentACar.Business.Concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.BrandService;
import com.kodlamaio.rentACar.Business.Requests.brand.CreateBrandRequest;
import com.kodlamaio.rentACar.Business.Requests.brand.DeleteBrandRequest;
import com.kodlamaio.rentACar.Business.Requests.brand.UpdateBrandRequest;
import com.kodlamaio.rentACar.Business.Responces.brand.GetBrandResponse;
import com.kodlamaio.rentACar.Business.Responces.brand.GetAllBrandResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessDataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.DataAccess.Abstracts.BrandRepository;
import com.kodlamaio.rentACar.Entities.Concretes.Brand;

@Service
public class BrandManager implements BrandService {
	private BrandRepository brandRepository;
	private ModelMapperService modelmapperService;

	@Autowired
	public BrandManager(BrandRepository brandRepository, ModelMapperService modelmapperService) {
		this.brandRepository = brandRepository;
		this.modelmapperService = modelmapperService;
	}

//BrandServiceImpl
	@Override
	public Result add(CreateBrandRequest createBrandRequest) {
		checkIfBrandExistsByName(createBrandRequest.getName());
		Brand brand = this.modelmapperService.forRequest().map(createBrandRequest, Brand.class);
		this.brandRepository.save(brand);
		return new SuccessResult();
		
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) {

		brandRepository.deleteById(deleteBrandRequest.getId());
		return new SuccessResult("Brand deleted");
	}

	@Override
	public DataResult<List<GetAllBrandResponse>> getall() {
		List<Brand> brands = this.brandRepository.findAll();
		List<GetAllBrandResponse> responce = brands.stream()
				.map(brand -> this.modelmapperService.forResponce().map(brand, GetAllBrandResponse.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetAllBrandResponse>>(responce);
	}

	@Override
	public DataResult<Brand> getById(GetBrandResponse getBrandResponce) {
		Brand brand = this.modelmapperService.forResponce().map(getBrandResponce, Brand.class);
		brand = brandRepository.findById(getBrandResponce.getId()).get();
		return new SuccessDataResult<Brand>(brand);
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {
		Brand colorToUpdate = this.modelmapperService.forRequest().map(updateBrandRequest, Brand.class);
		brandRepository.save(colorToUpdate);
		return new SuccessResult("Colord added");
	}
	private void checkIfBrandExistsByName(String name) 
	{
		Brand currentBrand = this.brandRepository.findByName(name);
		if (currentBrand!=null) {
			throw new BusinessException("BRAND EXİSTS");
		}
		
	}

}
