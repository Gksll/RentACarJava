package com.kodlamaio.rentACar.Entities.Concretes;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","maintenances","rentals","additionalServices"})

@Table(name = "cars")
public class Car {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "dailyPrice")
	private double dailyPrice;
	
	
	@Column(name = "state")
	private String state="avaliable";

	@Column(name = "plaque")
	private String plaque;
	@Column(name = "min_findex")
	private int minFindexScore;
	
	@Column(name = "pickupCityId")
	private Integer pickupCityId;
	
	@Column(name = "returnCityId")
	private Integer returnCityId;
	
	@Column(name = "kmcount")
	private String kmCount;
	
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
	
	@ManyToOne
	@JoinColumn(name = "color_id") 
	private Color color;
	
	@OneToMany(mappedBy = "car")
	List<Maintenance> maintenances;
	
	
	@OneToMany(mappedBy = "car")
	List<Rental> rentals;
	
	@ManyToOne
	@JoinColumn(name = "city_id") 
	private City city;
}
