package com.kodlamaio.rentACar.Entities.Concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","address"})
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "individual_customers")
@PrimaryKeyJoinColumn(name = "customer_id")
public class IndividualCustomer extends Customer {
	@Column(name = "identity_number")
	private String identityNumber;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "birth_Year")
	private Integer birthYear;
	
	
	@OneToMany(mappedBy = "individualCustomer")
	private List<IndividualInvoice> individualInvoices;
	
	@OneToMany(mappedBy = "individualCustomer")
	private List<IndividualRental> individualRentals;
	
}