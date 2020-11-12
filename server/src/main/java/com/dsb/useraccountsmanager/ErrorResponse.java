package com.dsb.useraccountsmanager;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {

	@JsonProperty("timestamp")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;

	@JsonProperty("status")
	private String status;

	@JsonProperty("statusCode")
	private Integer statusCode;

	@JsonProperty("message")
	private String message;

	public ErrorResponse() {
		this.timestamp = LocalDateTime.now();
	}

	public ErrorResponse(HttpStatus httpStatus, String message) {
		super();
		this.timestamp = LocalDateTime.now();
		this.status = httpStatus.getReasonPhrase();
		this.statusCode = httpStatus.value();
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String errorMessage) {
		this.message = errorMessage;
	}

}
