package com.app.dtos;


import com.app.model.PaymentType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductBuyData {

	@NotNull(message = "Product ID must not be null")
	private Long productId;

	@NotNull(message = "Quantity must not be null")
	@Positive(message = "quantity must be positive number")
	private Integer quantity;
	
	@NotNull(message = "Payment type must not be null")
	private PaymentType paymentType;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
}
