package com.app.service;

import java.util.List;

import com.app.dtos.ChangePassWord;
import com.app.dtos.ChangePassWordOTP;
import com.app.dtos.CustomerUpdate;
import com.app.dtos.OrderId;
import com.app.dtos.ProductBuyData;
import com.app.exceptions.CartException;
import com.app.exceptions.CustomerException;
import com.app.exceptions.OrderException;
import com.app.exceptions.ProductException;
import com.app.exceptions.ReviewException;
import com.app.model.Cart_Item;
import com.app.model.Customer;
import com.app.model.Order_Item;
import com.app.model.Product;
import com.app.model.Review;
import com.razorpay.RazorpayException;

public interface CustomerService {
	
	/**
	 * Registers customer 
	 * 
	 * @param customer -> customer object for register
	 * @return -> register confirmation message
	 */
	String registerCustomer(Customer customer)throws CustomerException;
	
	
	/**
	 * Updates the profile of a customer.
	 *
	 * @param customerData the customer data to update
	 * @return a message indicating the status of the profile update
	 */
	String updateCustomerProfile(CustomerUpdate customerData);

	
	/**
	 * 
	 * @return -> current customer obj
	 */
	Customer customerProfile();
	
	/**
	 * 
	 * @param password
	 * @return
	 * @throws CustomerException
	 */
	String changePasswordRequest(ChangePassWord password)throws CustomerException;
	
	/**
	 * 
	 * @param changePassWordOTP
	 * @return
	 * @throws CustomerException
	 */
	String verifyChangePasswordWithOtp(ChangePassWordOTP changePassWordOTP)throws CustomerException;
	
	/**
	 * 
	 * @return
	 * @throws ProductException
	 */
	Order_Item createOrder(ProductBuyData productBuyData)throws ProductException, RazorpayException;
	
	/**
	 * 
	 * @param buy -> contains product buying information such as productId, quantity
	 * @return -> message related buying product 
	 * @throws ProductException
	 */
	String confirmOrder(OrderId orderId) throws ProductException, OrderException, CustomerException;
	
	/**
	 * 
	 * @param productId -> to find product id
	 * @param review -> review object contains review information such as rating, comment
	 * @return -> message for successful review
	 */
	String addReview(Long productId, Review review)throws ProductException, ReviewException;
	
	
	/**
	 * 
	 * @param productId -> product id to find product
	 * @return -> message after successfully product is added to cart
	 */
	String addToCart(Long productId)throws ProductException, CartException;
	
	/**
	 * 
	 * @return -> all cart items of customer
	 */
	List<Cart_Item> getAllCartItem()throws CartException;
	
	/**
	 * 
	 * @param cartItemId -> cart item id to find cart item
	 * @param quantity -> quantity to change cart item quantity
	 * @return -> update quantity of cart item
	 */
	int updateCartItemQuantity(Long cartItemId, int quantityUpdateTyep)throws CartException;
	
	/**
	 * 
	 * @param cartItemId -> cart item id to find cart item
	 * @return -> message after successfully removing cart item
	 */
	String removeCartItem(Long cartItemId)throws CartException;
	
	/**
	 * 
	 * @param page -> current page
	 * @return -> list of order items
	 */
	List<Order_Item> getAllOrderItems(int page);
	
	
	
	
	
	
}
