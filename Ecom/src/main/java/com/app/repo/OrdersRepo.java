package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.Orders;

public interface OrdersRepo extends JpaRepository<Orders, Long>{

}
