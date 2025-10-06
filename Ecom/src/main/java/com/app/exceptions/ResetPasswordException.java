package com.app.exceptions;

public class ResetPasswordException extends RuntimeException{
	
	public ResetPasswordException() {}
	public ResetPasswordException(String msg) {
		super(msg);
	}
}
