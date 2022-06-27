package com.kodlamaio.rentACar.DataAccess.Abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kodlamaio.rentACar.Entities.Concretes.CorporateInvoice;
@Repository
public interface CorporateInvoiceRepository extends JpaRepository<CorporateInvoice, Integer> {

	
}
