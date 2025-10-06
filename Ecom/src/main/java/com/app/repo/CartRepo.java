package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Long>{

}
