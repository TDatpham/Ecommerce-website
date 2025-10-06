package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.app.dtos.ProductUpdateData;
import com.app.model.Admin;
import com.app.model.Category;
import com.app.model.Product;
import com.app.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/app/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerAdminHandler(@Valid @RequestBody Admin admin){
		
		String msg = adminService.registerAdmin(admin); 
		return new ResponseEntity<String>(msg, HttpStatus.CREATED);
		
	}
	
	@PostMapping("/category")
	public ResponseEntity<Category> addCategoryHandler(@Valid @RequestBody Category category){
		
		Category savedCategory = adminService.addCategory(category);
		return new ResponseEntity<Category>(savedCategory, HttpStatus.CREATED);
		
	}
	
	@PostMapping("/product/{categoryId}")
	public ResponseEntity<Product> addProductHandler(@PathVariable Long categoryId, @Valid @RequestBody Product product){
		
		Product savedProduct = adminService.addProduct(categoryId, product);
		return new ResponseEntity<Product>(savedProduct, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/product/update")
	public ResponseEntity<Void> updateProductHandler(@Valid @RequestBody ProductUpdateData productUpdateData){
		
		adminService.updateProduct(productUpdateData);
		return new ResponseEntity<Void>( HttpStatus.ACCEPTED);
	}
	@GetMapping("/users")
	public ResponseEntity<Long> getTotalUsersHandler() {
		
		Long totalUsers = adminService.totalUsers();
		return new ResponseEntity<Long>(totalUsers, HttpStatus.OK);
		
	}
}
