package com.example.products.model;

import org.springframework.http.HttpStatus;

public class DatavalidationException extends Exception {

	private static final long serialVersionUID = -533293247482361933L;

	private final String message;
	private final int errorCode;
	private final HttpStatus statusCode;

	public DatavalidationException(String message, int errorCode, HttpStatus statusCode) {
		super();
		this.message = message;
		this.errorCode = errorCode;
		this.statusCode = statusCode;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

}
