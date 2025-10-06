package com.app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long>{

	public Optional<Customer> findByEmail(String email);
	@Query("SELECT COUNT(c) FROM Customer c")
    Long countUsers();
}
