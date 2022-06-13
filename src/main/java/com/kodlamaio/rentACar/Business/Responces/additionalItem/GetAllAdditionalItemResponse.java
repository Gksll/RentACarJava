package com.kodlamaio.rentACar.Business.Responces.additionalItem;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllAdditionalItemResponse {
	private int id;
	private String name;
	private String description;
}
