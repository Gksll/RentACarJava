package com.kodlamaio.rentACar.Entities.Concretes;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "rentals")
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "pickup_date")
	private Date pickupDate;
	
	@Column(name = "return_date")
	private Date returnDate;
	
	@Column(name = "total_days")
	private int totalDays;
	
	@Column(name = "total_price")
	private double totalPrice;
	
	@Column(name = "pickup_city_id")
	private Integer pickupCityId;
	
	@Column(name = "return_city_id")
	private Integer returnCityId;
	
	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;
	
	
}
