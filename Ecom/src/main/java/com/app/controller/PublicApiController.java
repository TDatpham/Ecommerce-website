package com.app.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dtos.ApiResponse;
import com.app.dtos.ForgotPasswordRequest;
import com.app.dtos.ResetPasswordRequest;
import com.app.model.Category;
import com.app.model.Product;
import com.app.model.Review;
import com.app.service.PublicApiService;

@RestController
@RequestMapping("/app")
public class PublicApiController {
	
	@Autowired
	private PublicApiService publicApiService;
	
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<Product>> searchProductByKeyHandler(@RequestParam String q, @RequestParam int page){
		
		ApiResponse<Product> products = publicApiService.searchProducts(q, page);
		return new ResponseEntity<ApiResponse<Product>>(products, HttpStatus.OK);
			
	}
	
	
	@GetMapping("/search/rating")
	public ResponseEntity<ApiResponse<Product>> searchProductAndFilterByRatingHandler(@RequestParam int rating, @RequestParam String q, @RequestParam int page){
		
		ApiResponse<Product> products = publicApiService.searchProductAndFilterByRating(q, page, rating);
		return new ResponseEntity<ApiResponse<Product>>(products, HttpStatus.OK);
		
	}
	
	@GetMapping("/search/price")
	public ResponseEntity<ApiResponse<Product>> searchProductAndFilterByMinAndMaxPriceHandler( @RequestParam int min, @RequestParam int max, @RequestParam String q, @RequestParam int page){
		
		ApiResponse<Product> products = publicApiService.searchProductAndFilterByMinAndMaxPrice(q, page, min, max);
		return new ResponseEntity<ApiResponse<Product>>(products, HttpStatus.OK);
	}
	
	
	@GetMapping("/search/asc")
	public ResponseEntity<ApiResponse<Product>> searchProductAndSortByPriceAscHandler(@RequestParam String q, @RequestParam int page){
		
		ApiResponse<Product> products = publicApiService.searchProductAndSortByPriceAsc(q, page);
		return new ResponseEntity<ApiResponse<Product>>(products, HttpStatus.OK);
	}
	
	@GetMapping("/search/desc")
	public ResponseEntity<ApiResponse<Product>> searchProductAndSortByPriceDescHandler(@RequestParam String q, @RequestParam int page){
		
		ApiResponse<Product> products = publicApiService.searchProductAndSortByPriceDesc(q, page);
		return new ResponseEntity<ApiResponse<Product>>(products, HttpStatus.OK);
		
	}
	
	@GetMapping("/search/reviews/{productId}")
	public ResponseEntity<ApiResponse<Review>> getReviewsOfProduct(@PathVariable Long productId, @RequestParam int page){
		
		ApiResponse<Review> reviews = publicApiService.getReviewsByProduct(productId, page);
		return new ResponseEntity<ApiResponse<Review>>(reviews, HttpStatus.OK);
		
	}
	
	@GetMapping("/search/product/{pId}")
	public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long pId){
		
		Product product = publicApiService.getProductById(pId);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
		
	}
	
	
	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPasswordRequestHandler(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
		
		String msg = publicApiService.forgotPasswordRequest(forgotPasswordRequest);
		return new ResponseEntity<String>(msg, HttpStatus.CREATED);
		
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPasswordRequestHandler(@RequestBody ResetPasswordRequest resetPasswordRequest){
		
		String msg = publicApiService.passwordResetRequest(resetPasswordRequest);
		return new ResponseEntity<String>(msg, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/category/product/{cId}")
	public ResponseEntity<ApiResponse<Product>> findProductByCategoryIdHandler(@PathVariable Long cId, @RequestParam int page){
		
		ApiResponse<Product> products = publicApiService.findProductsByCategoryId(cId, page);
		return new ResponseEntity<ApiResponse<Product>>(products, HttpStatus.OK);
		
	}
	
	@GetMapping("/category")
	public ResponseEntity<List<Category>> find12CategoriesHandler(){
		
		List<Category> categories = publicApiService.getCategories();
		return new ResponseEntity<List<Category>>(categories, HttpStatus.CREATED);
		
	}

	
	
	
	
}
