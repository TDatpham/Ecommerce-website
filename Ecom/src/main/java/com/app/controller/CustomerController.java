package com.app.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dtos.ChangePassWord;
import com.app.dtos.ChangePassWordOTP;
import com.app.dtos.CustomerUpdate;
import com.app.dtos.OrderId;
import com.app.dtos.ProductBuyData;
import com.app.model.Cart_Item;
import com.app.model.Customer;
import com.app.model.Order_Item;
import com.app.model.Review;
import com.app.service.CustomerService;



import jakarta.validation.Valid;
import com.razorpay.*;



@RestController
@RequestMapping("/app/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	
	@PostMapping("/register")
	public ResponseEntity<String> customerRegisterHandler(@RequestBody Customer customer){
		
		String msg = customerService.registerCustomer(customer);
		return new ResponseEntity<String>(msg, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateCustomeProfileHandler(@RequestBody CustomerUpdate customerData){
		
		String msg = customerService.updateCustomerProfile(customerData);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
		
	}
	
	@GetMapping("/profile")
	public ResponseEntity<Customer> customerProfileHandler(){
		
		Customer customer = customerService.customerProfile();
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
		
	}
	
	@PostMapping("/change-password")
	public ResponseEntity<String> changePasswordRequestHandler(@RequestBody ChangePassWord changePassWord){
		
		String msg = customerService.changePasswordRequest(changePassWord);
		return new ResponseEntity<String>(msg, HttpStatus.ACCEPTED);
		
	}
	
	@PostMapping("/change-password/verify-otp")
	public ResponseEntity<String> verifyChangePasswordWithOtpHandler(@RequestBody ChangePassWordOTP changePassWord){
		
		String msg = customerService.verifyChangePasswordWithOtp(changePassWord);
		return new ResponseEntity<String>(msg, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/order/create/")
	public ResponseEntity<Order_Item> createOrder(@RequestBody ProductBuyData productBuyData) throws RazorpayException{
		
		Order_Item orderItem = customerService.createOrder(productBuyData);
		
		return new ResponseEntity<Order_Item>(orderItem, HttpStatus.CREATED);
	}
	
	
	@PostMapping("/product/buy/")
	public ResponseEntity<String> confirmOrderHandler(@Valid @RequestBody OrderId orderId){
		
		String msg = customerService.confirmOrder(orderId);
		return new ResponseEntity<String>(msg, HttpStatus.CREATED);
		
	}
	
	
	@PostMapping("/product/rating/{productId}")
	public ResponseEntity<String> addReviewHandler(@RequestBody Review review, @PathVariable Long productId){
		
		String msg = customerService.addReview(productId, review);
		return new ResponseEntity<String>(msg, HttpStatus.CREATED);
		
	}
	
	@PostMapping("/cart/{productId}")
	public ResponseEntity<String> addToCartHandler(@PathVariable Long productId) {
		
		String msg = customerService.addToCart(productId);
		return new ResponseEntity<String>(msg, HttpStatus.CREATED);
			
	}
	
	@GetMapping("/cart")
	public ResponseEntity<List<Cart_Item>> getAllCartItemsHandler(){
		
		List<Cart_Item> cartItems = customerService.getAllCartItem();
		return new ResponseEntity<List<Cart_Item>>(cartItems, HttpStatus.OK);
		
	}
	
	@PutMapping("/cart/{carItemId}")
	public ResponseEntity<Integer> updateCartItemQuantityHandler(@PathVariable Long carItemId, @RequestParam int type){
		
		int quantity = customerService.updateCartItemQuantity(carItemId, type);
		return new ResponseEntity<Integer>(quantity,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/cart/{cartItemId}")
	public ResponseEntity<String> deleteCartItemHandler(@PathVariable Long cartItemId){
		
		String msg = customerService.removeCartItem(cartItemId);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
		
	}
	
	
	@GetMapping("/orders")
	public ResponseEntity<List<Order_Item>> getAllOrderItemsHandler(){
		
		return new ResponseEntity<List<Order_Item>>(customerService.getAllOrderItems(0), HttpStatus.OK);
	}
}
