package com.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Admin;
import com.app.model.Customer;
import com.app.repo.AdminRepo;
import com.app.repo.CustomerRepo;

@RestController
@RequestMapping("/app")
public class LoginController {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private AdminRepo adminRepo;

	
	@GetMapping("/signIn")
	public ResponseEntity<String> getLoggedInCustomerDetailHandler(Authentication auth){
		
		String userName = auth.getName();
		
		
		Optional<Admin> optAdmin = adminRepo.findByEmail(userName);
		if(optAdmin.isPresent()) {
			
			return new ResponseEntity<String>("Hello "+optAdmin.get().getName(), HttpStatus.OK);
		}
		
		Optional<Customer> optCustomer = customerRepo.findByEmail(userName);
		if(optCustomer.isPresent()) {
			
			return new ResponseEntity<String>(optCustomer.get().getName(), HttpStatus.OK);
		}
		
		throw new BadCredentialsException("Invalid Username Or Password !");
		
	}
}
