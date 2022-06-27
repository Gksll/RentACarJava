package com.kodlamaio.rentACar.DataAccess.Abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kodlamaio.rentACar.Entities.Concretes.IndividualCustomer;
@Repository
public interface IndividualCustomerRepository extends JpaRepository<IndividualCustomer, Integer> {

	
}
