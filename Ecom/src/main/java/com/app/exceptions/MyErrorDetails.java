package com.app.exceptions;

import java.time.LocalDateTime;

public class MyErrorDetails {
	private LocalDateTime timestap;
	private String message;
	private String details;

	public MyErrorDetails() {}

	public MyErrorDetails(LocalDateTime timestap, String message, String details) {
		this.timestap = timestap;
		this.message = message;
		this.details = details;
	}

	public LocalDateTime getTimestap() {
		return timestap;
	}

	public void setTimestap(LocalDateTime timestap) {
		this.timestap = timestap;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "MyErrorDetails{" +
				"timestap=" + timestap +
				", message='" + message + '\'' +
				", details='" + details + '\'' +
				'}';
	}
}
