package com.dsb.useraccountsmanager;

public class BirthDateDeserializationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public BirthDateDeserializationException(String dateString) {
		this.message = String.format("Incorrect birth date format '%s'. Please use YYYY-MM-DD.", dateString);
	}
	
	@Override
	public String getMessage() {
		return message;
	}

}
