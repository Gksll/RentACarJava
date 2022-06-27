package com.kodlamaio.rentACar.Entities.Concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","address"})
@Table(name="corporate_customers")
@PrimaryKeyJoinColumn(name="customer_id") 
public class CorporateCustomer extends Customer{

	@Column(name="company_name")
	private String companyName;
	
	@OneToMany(mappedBy = "corporateCustomer")
	private List<CorporateInvoice> corporateInvoices;
	
	
}