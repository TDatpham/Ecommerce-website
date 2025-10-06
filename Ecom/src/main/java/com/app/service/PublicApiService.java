package com.app.service;

import java.util.List;

import com.app.dtos.ApiResponse;
import com.app.dtos.ForgotPasswordRequest;
import com.app.dtos.ResetPasswordRequest;
import com.app.exceptions.CategoryException;
import com.app.exceptions.ResetPasswordException;
import com.app.exceptions.ProductException;
import com.app.model.Category;
import com.app.model.Product;
import com.app.model.Review;

public interface PublicApiService {
	
	/**
	 * 
	 * @param productId -> unique id of product to find project object
	 * @return -> product object
	 */
	Product getProductById(Long productId) throws ProductException;
	
	/**
	 * 
	 * @param categoryId -> to find products with their respective category
	 * @param page -> page no of results
	 * @return -> list of products
	 * @throws CategoryException
	 * @throws ProductException
	 */
	ApiResponse<Product> findProductsByCategoryId(Long categoryId, int page)throws CategoryException, ProductException;
	
	/**
	 * 
	 * @return -> list of category
	 */
	List<Category> getCategories();
	
	/**
	 * 
	 * @param key -> product search key
	 * @param page -> page no of results
	 * @return -> list of products
	 */
	ApiResponse<Product> searchProducts(String key, int page)throws ProductException;
	
	/**
	 * 
	 * @param key -> product search key
	 * @param page -> page no of results
	 * @param rating -> rating for filtering search result
	 * @return -> list of products 
	 */
	ApiResponse<Product> searchProductAndFilterByRating(String key, int page, int rating)throws ProductException;
	
	/**
	 * 
	 * @param key -> product search key
	 * @param page ->  page no of results
	 * @param minPrice -> min product price to filter products
	 * @param maxPrice -> max product price to filter products
	 * @return -> list of products 
	 */
	ApiResponse<Product> searchProductAndFilterByMinAndMaxPrice(String key, int page, int minPrice, int maxPrice)throws ProductException;
	
	/**
	 * 
	 * @param key -> product search key
	 * @param page ->  page no of results
	 * @return -> list of products sorted by prices in ascending order
	 */
	ApiResponse<Product> searchProductAndSortByPriceAsc(String key, int page)throws ProductException;
	
	/**
	 * 
	 * @param  key -> product search key
	 * @param page ->  page no of results
	 * @return -> list of products sorted by prices in descending order
	 */
	ApiResponse<Product> searchProductAndSortByPriceDesc(String key, int page)throws ProductException;
	
	/**
	 * 
	 * @param prodductId -> product id to find product
	 * @param page -> current page
	 * @return -> List of Reviews of product
	 */
	ApiResponse<Review> getReviewsByProduct(Long productId, int page);
	
	/**
	 * 
	 * @param forgotPasswordRequest -> contains user email
	 * @return -> return message related this request(we have sent an otp to your email)
	 */
	String forgotPasswordRequest(ForgotPasswordRequest forgotPasswordRequest)throws ResetPasswordException;
	
	/**
	 * 
	 * @param resetPasswordRequest -> contains user email, new password and otp
	 * @return -> return message related this request(password changed successfully)
	 * @throws ResetPasswordException 
	 */
	String passwordResetRequest(ResetPasswordRequest resetPasswordRequest)throws ResetPasswordException;
	
	
}
