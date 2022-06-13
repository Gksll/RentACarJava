package com.kodlamaio.rentACar.Business.Requests.car;

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
private String description;
private double dailyPrice;
private String state;
private String plaque;
private String kmCount;
private Integer pickupCityId;
private Integer returnCityId;
}
