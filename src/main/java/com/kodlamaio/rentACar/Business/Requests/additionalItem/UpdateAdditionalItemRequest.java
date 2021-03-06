package com.kodlamaio.rentACar.Business.Requests.additionalItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdditionalItemRequest {
	private int id;
	private String name;
	private String description;
	private double price;
}
