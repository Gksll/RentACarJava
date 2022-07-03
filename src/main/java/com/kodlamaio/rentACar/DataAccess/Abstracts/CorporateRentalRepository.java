package com.kodlamaio.rentACar.DataAccess.Abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kodlamaio.rentACar.Entities.Concretes.CorporateRental;

@Repository
public interface CorporateRentalRepository extends JpaRepository<CorporateRental, Integer> {
}
