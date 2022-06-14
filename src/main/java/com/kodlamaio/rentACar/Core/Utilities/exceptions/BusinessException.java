package com.kodlamaio.rentACar.Core.Utilities.exceptions;

@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {
	public BusinessException(String message) {
		super(message);
	}
}
