package com.kodlamaio.rentACar.DataAccess.Abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.rentACar.Entities.Concretes.Rental;

public interface RentalRepository extends JpaRepository<Rental, Integer>{
}
