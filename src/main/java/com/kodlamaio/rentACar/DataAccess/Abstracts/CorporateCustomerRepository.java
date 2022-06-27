package com.kodlamaio.rentACar.DataAccess.Abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kodlamaio.rentACar.Entities.Concretes.CorporateCustomer;
@Repository
public interface CorporateCustomerRepository extends JpaRepository<CorporateCustomer, Integer> {

}
