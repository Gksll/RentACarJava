package com.kodlamaio.rentACar.DataAccess.Abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.rentACar.Entities.Concretes.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer>{
Brand findByName(String name);
}
