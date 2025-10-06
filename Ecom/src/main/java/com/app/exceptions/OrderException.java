package com.app.exceptions;

public class OrderException extends RuntimeException{
	
	public OrderException() {}
	public OrderException(String msg) {
		super(msg);
	}

}
