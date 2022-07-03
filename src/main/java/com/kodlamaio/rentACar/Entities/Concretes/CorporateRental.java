package com.kodlamaio.rentACar.Entities.Concretes;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@Table(name = "corporate_rentals")
public class CorporateRental extends Rental {
	
	@OneToMany(mappedBy = "corporateRental")
	List<CorporateInvoice> corporateInvoices ;

	@ManyToOne
	@JoinColumn(name = "corporate_customer_id")
	private CorporateCustomer corporateCustomer;
}
