package com.kodlamaio.rentACar.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.BrandService;
import com.kodlamaio.rentACar.Business.Requests.brand.CreateBrandRequest;
import com.kodlamaio.rentACar.Business.Requests.brand.DeleteBrandRequest;
import com.kodlamaio.rentACar.Business.Requests.brand.UpdateBrandRequest;
import com.kodlamaio.rentACar.Business.Responces.brand.GetBrandResponse;
import com.kodlamaio.rentACar.Business.Responces.brand.GetAllBrandResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Entities.Concretes.Brand;

@RestController
@RequestMapping("api/brands")
public class BrandsController {
	@Autowired
	private BrandService brandService;

	
	
	@PostMapping("/add")
	public Result add( @RequestBody CreateBrandRequest createBrandRequest) {
		brandService.add(createBrandRequest);
		return new SuccessResult();
	}
	@PostMapping("/delete")
	public Result delete(DeleteBrandRequest deleteBrandRequest) {
		brandService.delete(deleteBrandRequest);
		return new SuccessResult();
	}

	@PostMapping("/update")
	public Result update(@RequestBody UpdateBrandRequest updateBrandRequest) {
		brandService.update(updateBrandRequest);
		return new SuccessResult();
	}

	@GetMapping("/getall")
	public DataResult<List<GetAllBrandResponse>> GetAll() {
		return this.brandService.getall();
//		return new SuccessDataResult<List<Brand>>(brandService.getall().getData());
	}
	@GetMapping("/getbyid")
	public DataResult<Brand> GetById(GetBrandResponse getBrandResponce) {
		return this.brandService.getById(getBrandResponce);
//		return new SuccessDataResult<Brand>(brandService.getById(id).getData());
	}
}
