package com.kodlamaio.rentACar.DataAccess.Abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.rentACar.Entities.Concretes.AdditionalItem;

public interface AdditionalItemRepository extends JpaRepository<AdditionalItem, Integer>{
	AdditionalItem	findByName(String name);
}
