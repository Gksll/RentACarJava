package com.kodlamaio.rentACar.Business.Abstracts;

import java.util.List;

import com.kodlamaio.rentACar.Business.Requests.brand.CreateBrandRequest;
import com.kodlamaio.rentACar.Business.Requests.brand.DeleteBrandRequest;
import com.kodlamaio.rentACar.Business.Requests.brand.UpdateBrandRequest;
import com.kodlamaio.rentACar.Business.Responces.brand.GetBrandResponse;
import com.kodlamaio.rentACar.Business.Responces.brand.GetAllBrandResponse;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.Brand;

public interface BrandService {
Result add(CreateBrandRequest createBrandRequest);
//responce=dto request=brand patern
Result delete(DeleteBrandRequest deleteBrandRequest);
Result update(UpdateBrandRequest updateBrandRequest);
DataResult<List<GetAllBrandResponse>> getall();
DataResult<Brand> getById(GetBrandResponse getBrandResponce);

}
