package com.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
 

@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cId;
	
	@NotNull(message = "name can not be null")
	@Size(min=3, message="name length should be atleast 3")
    private String name;
	
	@NotNull(message = "email cannot be null")
	@Column(unique = true)
	@Email(message = "")
	private String email;
	
	@Size(min = 8, message = "Password should be at least 8 characters.")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@Size(min=3, message="city length should be atleast 3")
	private String city;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String role;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
	@JsonManagedReference
	private Cart cart;
	
	@JsonIgnore
	@JsonManagedReference
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
	private Orders orders;

	public Long getcId() {
		return cId;
	}

	public void setcId(Long cId) {
		this.cId = cId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}
}
