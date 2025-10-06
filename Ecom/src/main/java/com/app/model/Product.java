package com.app.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
 

@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pId;
	@NotNull(message = "product name can not be null")
	@Size(min=20, message = "product name length should be atleast 20")
	private String productName;
	@NotNull(message = "product description can not be null")
	@Size(min=40, message = "product description length should be atleast 20")
	private String productDescription;
	@NotNull
	@Min(1)
	private Integer price;
	private Double rating;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Long ratingSum;
//	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Long ratingCount;
	@NotNull(message = "product image can not be null")
	private String image;
	@Min(0)
	private Long stocks;
	private Long sold;
	private Boolean isOutOfStock;
	
	public Product() {
		this.sold = 0l;
		this.stocks = 0l;
		this.rating = 0d;
		this.ratingSum = 0l;
		this.ratingCount = 0l;
		this.isOutOfStock = false;
	}
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	private List<Review> reviews;
	
	@ManyToOne
	@JsonBackReference
	private Category category;

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
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

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Long getRatingSum() {
		return ratingSum;
	}

	public void setRatingSum(Long ratingSum) {
		this.ratingSum = ratingSum;
	}

	public Long getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(Long ratingCount) {
		this.ratingCount = ratingCount;
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

	public Long getSold() {
		return sold;
	}

	public void setSold(Long sold) {
		this.sold = sold;
	}

	public Boolean getIsOutOfStock() {
		return isOutOfStock;
	}

	public void setIsOutOfStock(Boolean isOutOfStock) {
		this.isOutOfStock = isOutOfStock;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
