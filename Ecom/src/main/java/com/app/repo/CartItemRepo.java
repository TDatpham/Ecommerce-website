package com.app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.model.Cart_Item;

public interface CartItemRepo extends JpaRepository<Cart_Item, Long>{
	
	@Query("from Cart_Item c where c.productId = :productId and c.user = :user")
	Optional<Cart_Item> findByProductIdAndUser(@Param("productId") Long productId, @Param("user") String user);
	

}
