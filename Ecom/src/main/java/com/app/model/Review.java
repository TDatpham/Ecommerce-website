package com.app.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
 

@Entity
public class Review {
	
	@Id
	@GeneratedValue
	private Long reviewId;
	@NotNull
	private String ratingStatus;
	
	@NotNull
	@Size(min=3, message="review length should be atleast 3")
	private String description;
	
	@NotNull
	@Min(1)
	@Max(5)
	private Integer rating;
	
	private String customerName;
	private String customerCity;
	
	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public String getRatingStatus() {
		return ratingStatus;
	}

	public void setRatingStatus(String ratingStatus) {
		this.ratingStatus = ratingStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCity() {
		return customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}
}
