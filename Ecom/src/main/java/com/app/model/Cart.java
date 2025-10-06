package com.app.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
 

@Entity
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cartId;
	
	@OneToOne
	@JsonBackReference
	private Customer customer;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Cart_Item> cartItems;

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Cart_Item> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<Cart_Item> cartItems) {
		this.cartItems = cartItems;
	}
}
