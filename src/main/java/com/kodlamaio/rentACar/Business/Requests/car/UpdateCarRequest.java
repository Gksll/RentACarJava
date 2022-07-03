package com.kodlamaio.rentACar.Business.Requests.car;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarRequest {
	private int id;
	private int brandId;
	private int colorId;
	@NotEmpty
	@NotNull(message = "")
	@Size(min = 2)
	private String description;
	@Min(value = 10)
	private double dailyPrice;
	private String state;
	private String plaque;
	private String kmCount;
	private int minFindexScore;
}
