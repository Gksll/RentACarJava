package com.kodlamaio.rentACar.DataAccess.Abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kodlamaio.rentACar.Entities.Concretes.Car;
@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
	List<Car> getByBrandId(int brandId);
}
