package com.kodlamaio.rentACar.Entities.Concretes;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","additionalService","individualRental"})
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "invoices")
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "invoice_number")
	private int invoiceNumber;
	
	@Column(name = "total_price")
	private double totalPrice;
	
	@Column(name = "state")
	private boolean state;
	
	@ManyToOne
	@JoinColumn(name = "additional_service_id")
	private AdditionalService additionalService;
	
}
