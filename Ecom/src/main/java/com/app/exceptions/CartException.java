package com.app.exceptions;

public class CartException extends RuntimeException{
	
	public CartException() {}
	
	public CartException(String msg) {
		super(msg);
	}

}
