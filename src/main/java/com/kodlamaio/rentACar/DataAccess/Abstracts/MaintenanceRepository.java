package com.kodlamaio.rentACar.DataAccess.Abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.rentACar.Entities.Concretes.Maintenance;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer>{

}
