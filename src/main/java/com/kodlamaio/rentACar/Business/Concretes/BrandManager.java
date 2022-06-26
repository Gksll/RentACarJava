package com.kodlamaio.rentACar.Business.Concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.BrandService;
import com.kodlamaio.rentACar.Business.Requests.brand.CreateBrandRequest;
import com.kodlamaio.rentACar.Business.Requests.brand.DeleteBrandRequest;
import com.kodlamaio.rentACar.Business.Requests.brand.UpdateBrandRequest;
import com.kodlamaio.rentACar.Business.Responces.brand.GetAllBrandResponse;
import com.kodlamaio.rentACar.Business.Responces.brand.GetBrandResponse;
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
	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private ModelMapperService modelmapperService;

	@Override
	public Result add(CreateBrandRequest createBrandRequest) {
		checkIfBrandExistsByName(createBrandRequest.getName());
		Brand brand = this.modelmapperService.forRequest().map(createBrandRequest, Brand.class);
		brandRepository.save(brand);
		return new SuccessResult(brand.getName()+" added successfully");
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) {
		checkIfBrandExistsById(deleteBrandRequest.getId());
		brandRepository.deleteById(deleteBrandRequest.getId());
		return new SuccessResult(deleteBrandRequest.getId()+" deleted successfully");
	}

	@Override
	public DataResult<List<GetAllBrandResponse>> getAll() {
		List<Brand> brands = this.brandRepository.findAll();
		List<GetAllBrandResponse> responce = brands.stream()
				.map(brand -> this.modelmapperService.forResponce().map(brand, GetAllBrandResponse.class))
				.collect(Collectors.toList());
			return new SuccessDataResult<List<GetAllBrandResponse>>(responce," the brands was successfully listed");
	}

	@Override
	public DataResult<Brand> getById(GetBrandResponse getBrandResponse) {
		checkIfBrandExistsById(getBrandResponse.getId());
		Brand brand = this.modelmapperService.forResponce().map(getBrandResponse, Brand.class);
		brand = brandRepository.findById(getBrandResponse.getId()).get();
		return new SuccessDataResult<Brand>(brand,"the brand was successfully listed");
	}
	
	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {
		checkIfBrandExistsById(updateBrandRequest.getId());
		Brand brandToUpdate = this.modelmapperService.forRequest().map(updateBrandRequest, Brand.class);
		brandRepository.save(brandToUpdate);
		return new SuccessResult("brand updated");
	}
	private void checkIfBrandExistsByName(String name) 
	{
		Brand currentBrand = brandRepository.findByName(name);
		if (currentBrand!=null) {
			throw new BusinessException("BRAND EXISTS");
		}
	}
	private void checkIfBrandExistsById(int id) 
	{
		boolean result = brandRepository.existsById(id);
		if (result==false) {
			throw new BusinessException("BRAND NOT EXISTS");
		}
	}

}
