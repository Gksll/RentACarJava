package com.kodlamaio.rentACar.DataAccess.Abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kodlamaio.rentACar.Entities.Concretes.RentalDetail;
@Repository
public interface RentalDetailRepository extends JpaRepository<RentalDetail, Integer> {

	
}
