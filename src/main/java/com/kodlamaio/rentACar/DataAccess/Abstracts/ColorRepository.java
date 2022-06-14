package com.kodlamaio.rentACar.DataAccess.Abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kodlamaio.rentACar.Entities.Concretes.Color;
@Repository
public interface ColorRepository extends JpaRepository<Color, Integer>{
	Color findByName(String name);
}
