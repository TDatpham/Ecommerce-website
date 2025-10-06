package com.app.dtos;

import jakarta.validation.constraints.NotNull;

public class OrderId {
	
	@NotNull
	private Long orderId;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
