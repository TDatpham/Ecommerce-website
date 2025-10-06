package com.app.utils;

public class FieldValidationUtil {

	// Validate minimum value for fields like price and stocks
	public static void validateMinValue(Integer value, int minValue, String fieldName) {
		if (value != null && value < minValue) {
			throw new IllegalArgumentException(fieldName + " must be at least " + minValue);
		}
	}

	public static void validateMinValue(Long value, long minValue, String fieldName) {
		if (value != null && value < minValue) {
			throw new IllegalArgumentException(fieldName + " must be at least " + minValue);
		}
	}
	
	// Validate minimum size for fields like productName, productDescription, and
	public static void validateMinSize(String value, int minSize, String fieldName) {
			if (value != null && value.length() < minSize) {
				throw new IllegalArgumentException(fieldName + " must be least " + minSize + " characters");
			}
	}

	// Validate maximum size for fields like productName, productDescription, and
	// image
	public static void validateMaxSize(String value, int maxSize, String fieldName) {
		if (value != null && value.length() > maxSize) {
			throw new IllegalArgumentException(fieldName + " must not exceed " + maxSize + " characters");
		}
	}

	// Validate required fields
	public static void validateRequired(Object value, String fieldName) {
		if (value == null) {
			throw new IllegalArgumentException(fieldName + " is required and cannot be null");
		}
	}
}
