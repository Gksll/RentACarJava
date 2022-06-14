package com.kodlamaio.rentACar.DataAccess.Abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kodlamaio.rentACar.Entities.Concretes.AdditionalItem;
@Repository
public interface AdditionalItemRepository extends JpaRepository<AdditionalItem, Integer>{
	AdditionalItem	findByName(String name);
}
