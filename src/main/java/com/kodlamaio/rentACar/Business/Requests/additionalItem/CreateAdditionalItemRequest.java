package com.kodlamaio.rentACar.Business.Requests.additionalItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdditionalItemRequest {
	private String name;
	private String description;
	private Integer price;
}
