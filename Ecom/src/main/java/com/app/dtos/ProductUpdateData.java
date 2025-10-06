package com.app.dtos;


import jakarta.validation.constraints.NotNull;

public class ProductUpdateData {
	
	@NotNull(message = "productId can not be null")
	private Long productId;
	
	private String productName;
	private String productDescription;
	private Integer price;
	private String image;
	private Long stocks;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getStocks() {
		return stocks;
	}

	public void setStocks(Long stocks) {
		this.stocks = stocks;
	}
}
