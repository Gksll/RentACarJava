package com.kodlamaio.rentACar.Business.Responces.additionalService;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllAdditionalServiceResponse {
	private int id;
	private int day;
	private double totalPrice;
	private int additionalItemId;
	private Integer price;
	private Date sendDate;
	private Date returnDate;
}
