package com.kodlamaio.rentACar.DataAccess.Abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kodlamaio.rentACar.Entities.Concretes.Brand;
@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer>{
Brand findByName(String name);
}
