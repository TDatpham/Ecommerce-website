package com.app.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	// AdminException
	@ExceptionHandler(AdminException.class)
	public ResponseEntity<MyErrorDetails> myAdminExceptionHandler(AdminException ae, WebRequest wr) {
		MyErrorDetails err = new MyErrorDetails();
		err.setTimestap(LocalDateTime.now());
		err.setMessage(ae.getMessage());
		err.setDetails(wr.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	// Customer Exception
	@ExceptionHandler(CustomerException.class)
	public ResponseEntity<MyErrorDetails> customerExceptionHandler(CustomerException ce, WebRequest wr) {
		MyErrorDetails err = new MyErrorDetails();
		err.setTimestap(LocalDateTime.now());
		err.setMessage(ce.getMessage());
		err.setDetails(wr.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	// Cart Exception
	@ExceptionHandler(CartException.class)
	public ResponseEntity<MyErrorDetails> cartExceptionHandler(CartException ce, WebRequest wr) {
		MyErrorDetails err = new MyErrorDetails();
		err.setTimestap(LocalDateTime.now());
		err.setMessage(ce.getMessage());
		err.setDetails(wr.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	// Category Exception
	@ExceptionHandler(CategoryException.class)
	public ResponseEntity<MyErrorDetails> categoryExceptionHandler(CategoryException ce, WebRequest wr) {
		MyErrorDetails err = new MyErrorDetails();
		err.setTimestap(LocalDateTime.now());
		err.setMessage(ce.getMessage());
		err.setDetails(wr.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	// Product Exception
	@ExceptionHandler(ProductException.class)
	public ResponseEntity<MyErrorDetails> productExceptionHandler(ProductException pe, WebRequest wr) {
		MyErrorDetails err = new MyErrorDetails();
		err.setTimestap(LocalDateTime.now());
		err.setMessage(pe.getMessage());
		err.setDetails(wr.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	// Review Exception
	@ExceptionHandler(ReviewException.class)
	public ResponseEntity<MyErrorDetails> reviewExceptionHandler(ReviewException re, WebRequest wr) {
		MyErrorDetails err = new MyErrorDetails();
		err.setTimestap(LocalDateTime.now());
		err.setMessage(re.getMessage());
		err.setDetails(wr.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	// Order Exception
	@ExceptionHandler(OrderException.class)
	public ResponseEntity<MyErrorDetails> orderExceptionHandler(OrderException oe, WebRequest wr) {
		MyErrorDetails err = new MyErrorDetails();
		err.setTimestap(LocalDateTime.now());
		err.setMessage(oe.getMessage());
		err.setDetails(wr.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	// Reset Password Exception Handler
	@ExceptionHandler(ResetPasswordException.class)
	public ResponseEntity<MyErrorDetails> resetPasswordExceptionHandler(ResetPasswordException rpe, WebRequest wr) {
		MyErrorDetails err = new MyErrorDetails();
		err.setTimestap(LocalDateTime.now());
		err.setMessage(rpe.getMessage());
		err.setDetails(wr.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	

	// global exception handleR
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> myExceptionHandler(Exception e, WebRequest wr) {
		MyErrorDetails err = new MyErrorDetails();
		err.setTimestap(LocalDateTime.now());
		err.setMessage(e.getMessage());
		err.setDetails(wr.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	// NoHandlerFound Exception
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<MyErrorDetails> noHandlerFoundExceptionHandler(NoHandlerFoundException nfe, WebRequest wr) {
		MyErrorDetails err = new MyErrorDetails();
		err.setTimestap(LocalDateTime.now());
		err.setMessage(nfe.getMessage());
		err.setDetails(wr.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	// Validation Exception Handler
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyErrorDetails> validationExceptionHandler(MethodArgumentNotValidException me,
			WebRequest wr) {
		MyErrorDetails err = new MyErrorDetails();
		err.setTimestap(LocalDateTime.now());
		
		// combine all error messages
		StringBuilder errorMessages = new StringBuilder();
		me.getBindingResult().getFieldErrors().forEach(error -> {
		    errorMessages.append(error.getDefaultMessage()).append("\n");
		});

		
		err.setMessage(errorMessages.toString());
		err.setDetails(me.getBindingResult().getFieldError().getDefaultMessage());

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

}
