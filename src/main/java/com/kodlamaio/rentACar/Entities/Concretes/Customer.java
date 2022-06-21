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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","address"})
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	 private int Id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "birth_year")
	private Integer birthYear;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "tc_No")
	private String tcNo;
	@Column(name="email")
	private String email;
	@Column(name="password")
	private String password;
	
	@OneToMany(mappedBy = "customer")
	private List<Rental> rentals;
	
	@OneToMany(mappedBy = "customer")
	private List<RentalDetail> rentalDetails;
	
	@ManyToOne()
	@JoinColumn(name = "address_id")
	private Address address;

}
