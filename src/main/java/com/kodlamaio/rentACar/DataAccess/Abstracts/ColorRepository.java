package com.kodlamaio.rentACar.DataAccess.Abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.rentACar.Entities.Concretes.Color;

public interface ColorRepository extends JpaRepository<Color, Integer>{
	Color findByName(String name);
}
