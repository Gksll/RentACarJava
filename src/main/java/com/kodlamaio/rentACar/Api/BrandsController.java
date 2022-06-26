package com.kodlamaio.rentACar.Api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.Business.Abstracts.BrandService;
import com.kodlamaio.rentACar.Business.Requests.brand.CreateBrandRequest;
import com.kodlamaio.rentACar.Business.Requests.brand.DeleteBrandRequest;
import com.kodlamaio.rentACar.Business.Requests.brand.UpdateBrandRequest;
import com.kodlamaio.rentACar.Business.Responces.brand.GetAllBrandResponse;
import com.kodlamaio.rentACar.Business.Responces.brand.GetBrandResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.Brand;

@RestController
@RequestMapping("api/brands")
public class BrandsController {
	private BrandService brandService;

	public BrandsController(BrandService brandService) {
		this.brandService = brandService;
	}

	@PostMapping("/add")
	public Result Add(@RequestBody CreateBrandRequest createBrandRequest) {
		return brandService.add(createBrandRequest);
	}

	@PostMapping("/delete")
	public Result Delete(DeleteBrandRequest deleteBrandRequest) {
		return brandService.delete(deleteBrandRequest);
	}

	@PostMapping("/update")
	public Result Update(@RequestBody UpdateBrandRequest updateBrandRequest) {
		return brandService.update(updateBrandRequest);

	}

	@GetMapping("/getAll")
	public DataResult<List<GetAllBrandResponse>> GetAll() {
		return brandService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<Brand> GetById(GetBrandResponse getBrandResponce) {
		return brandService.getById(getBrandResponce);
	}
}
