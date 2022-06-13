package com.kodlamaio.rentACar.Business.Responces.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCarResponce {
	private int id;
	private int brandId;
	private int colorId;
	private String description;
	private double dailyPrice;
	private String state="avaliable";
	private String plaque;
	private String kmCount;
	private Integer pickupCityId;
	private Integer returnCityId;
}
