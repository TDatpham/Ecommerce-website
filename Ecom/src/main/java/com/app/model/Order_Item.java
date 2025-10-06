package com.app.model;

import com.app.dtos.PaymentInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
 

@Entity
public class Order_Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderItemId;
	
	@NotNull
	private Long productId;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String user;
	
	@Positive(message = "quantity can't be negative")
	private Integer quantity;
	
	private String image;
	
	@Positive(message = "price can't be negative")
	private Integer price;
	
	@NotNull
	private String product;
	
	 @Enumerated(EnumType.STRING)
	private PaymentType paymentType;
	 
	 @Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	 
	 @Enumerated(EnumType.STRING)
	 private OrderStatus orderStatus = OrderStatus.PENDING;
	 
	 
	 @Transient
	 private PaymentInfo paymentInfo;

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
}
