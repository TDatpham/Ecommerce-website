package com.app.model;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonBackReference;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderId;
	
	@OneToOne
	@JsonBackReference
	private Customer customer;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Order_Item> orderItems;

	public void setCustomer(Customer customer2) {
		// TODO Auto-generated method stub
		this.customer=customer2;
	}

	public List<Order_Item> getOrderItems() {
		// TODO Auto-generated method stub
		return orderItems;
	}
	

}
