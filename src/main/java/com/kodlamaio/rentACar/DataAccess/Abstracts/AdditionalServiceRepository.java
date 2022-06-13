package com.kodlamaio.rentACar.DataAccess.Abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.rentACar.Entities.Concretes.AdditionalService;

public interface AdditionalServiceRepository extends JpaRepository<AdditionalService, Integer>{

}
