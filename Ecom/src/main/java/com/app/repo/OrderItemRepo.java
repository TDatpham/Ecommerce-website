package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.model.Order_Item;

public interface OrderItemRepo extends JpaRepository<Order_Item, Long>{
	
	@Query("from Order_Item o where o.user = :user and o.productId = :productId")
	List<Order_Item> findOrderItemByUserAndProductId(@Param("user") String user, @Param("productId") Long productId);
}
