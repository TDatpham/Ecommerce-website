package com.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.model.Admin;
import com.app.model.Customer;
import com.app.repo.AdminRepo;
import com.app.repo.CustomerRepo;

@Service
public class CustomerUserDetailService implements UserDetailsService{
	
	private final CustomerRepo customerRepo;
    private final AdminRepo adminRepo;
	
	public CustomerUserDetailService(CustomerRepo customerRepo, AdminRepo adminRepo) {
		this.customerRepo = customerRepo;
		this.adminRepo = adminRepo;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Customer> optCustomer = customerRepo.findByEmail(username);
		if(optCustomer.isPresent()) {
			
			Customer customer = optCustomer.get();
	
			List<GrantedAuthority> authorities = new ArrayList<>();
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(customer.getRole());
			authorities.add(sga);
			
			return new User(customer.getEmail(), customer.getPassword(), authorities);
		}
		
		Optional<Admin> optAdmin = adminRepo.findByEmail(username);
		if(optAdmin.isPresent()) {
			
			Admin admin = optAdmin.get();
			List<GrantedAuthority> authorities = new ArrayList<>();
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(admin.getRole());
			authorities.add(sga);
			
			return new User(admin.getEmail(), admin.getPassword(), authorities);
		}
		throw new UsernameNotFoundException("User not found with username: " + username);
	}

}
