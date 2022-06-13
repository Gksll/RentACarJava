package com.kodlamaio.rentACar.Business.Abstracts;

import java.util.List;

import com.kodlamaio.rentACar.Business.Requests.car.CreateCarRequest;
import com.kodlamaio.rentACar.Business.Requests.car.DeleteCarRequest;
import com.kodlamaio.rentACar.Business.Requests.car.UpdateCarRequest;
import com.kodlamaio.rentACar.Business.Responces.car.GetAllCarResponce;
import com.kodlamaio.rentACar.Business.Responces.car.GetCarResponce;
import com.kodlamaio.rentACar.Core.Utilities.Results.DataResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Entities.Concretes.Car;

public interface CarService {
Result add(CreateCarRequest createCarRequest);
Result delete(DeleteCarRequest deleteCarRequest);
Result update(UpdateCarRequest updateCarRequest);
DataResult<Car> getById(GetCarResponce getCarResponce);
DataResult<List<GetAllCarResponce>> getAll();
int checkBrands(Car car);
}
