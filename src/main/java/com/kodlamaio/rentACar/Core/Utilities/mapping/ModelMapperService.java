package com.kodlamaio.rentACar.Core.Utilities.mapping;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {
ModelMapper forResponce();
ModelMapper forRequest();
}
