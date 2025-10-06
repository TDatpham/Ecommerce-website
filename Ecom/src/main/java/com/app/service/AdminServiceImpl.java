package com.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dtos.ProductUpdateData;
import com.app.exceptions.AdminException;
import com.app.exceptions.CategoryException;
import com.app.exceptions.ProductException;
import com.app.model.Admin;
import com.app.model.Category;
import com.app.model.Customer;
import com.app.model.Product;
import com.app.repo.AdminRepo;
import com.app.repo.CategoryRepo;
import com.app.repo.CustomerRepo;
import com.app.repo.ProductRepo;
import com.app.utils.FieldValidationUtil;

@Service
public class AdminServiceImpl implements AdminService {

	private final AdminRepo adminRepo;
	private final CustomerRepo customerRepo;
	private final PasswordEncoder passwordEncoder;
	private final CategoryRepo categoryRepo;
	private final ProductRepo productRepo;

	@Autowired
	public AdminServiceImpl(AdminRepo adminRepo, CustomerRepo customerRepo, PasswordEncoder passwordEncoder,
			CategoryRepo categoryRepo, ProductRepo productRepo) {
		this.adminRepo = adminRepo;
		this.customerRepo = customerRepo;
		this.passwordEncoder = passwordEncoder;
		this.categoryRepo = categoryRepo;
		this.productRepo = productRepo;
	}

	@Override
	public String registerAdmin(Admin admin) {

		Optional<Customer> optCustomer = customerRepo.findByEmail(admin.getEmail());
		if (optCustomer.isPresent()) {
			throw new AdminException("duplicate email !");
		}

		admin.setRole("ADMIN");
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		adminRepo.save(admin);
		return "Admin Registerd Successfully";
	}

	@Override
	public Category addCategory(Category category) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		Optional<Admin> optAdmin = adminRepo.findByEmail(userName);

		categoryRepo.save(category);

		Admin admin = optAdmin.get();

		category.setAdmin(admin);
		admin.getCategories().add(category);

		adminRepo.save(admin);

		return category;

	}

	@Override
	public Product addProduct(Long categoryId, Product product) {

		Optional<Category> optCategory = categoryRepo.findById(categoryId);
		if (optCategory.isEmpty()) {
			throw new CategoryException("Invalid category id " + categoryId);
		}

		System.out.println(product.getImage());
		System.out.println(product.getPrice());
		Category category = optCategory.get();
		product.setCategory(category);
		category.getProducts().add(product);

		categoryRepo.save(category);

		return product;

	}

	@Override
	public void updateProduct(ProductUpdateData productUpdateData) {

		Optional<Product> optProduct = productRepo.findById(productUpdateData.getProductId());
		if (optProduct.isEmpty()) {
			throw new ProductException("Invalid Product Id!");
		}

		Product product = optProduct.get();
		// gotta check if this product belongs to current customer or not

		if (productUpdateData.getProductName() != null) {

			// validate product name
			FieldValidationUtil.validateMinSize(productUpdateData.getProductName(), 10, "Product Name");
			FieldValidationUtil.validateMaxSize(productUpdateData.getProductName(), 200, "Product Name");
			product.setProductName(productUpdateData.getProductName());
		}

		if (productUpdateData.getProductDescription() != null) {

			// validate product description
			FieldValidationUtil.validateMinSize(productUpdateData.getProductDescription(), 100, "Product Description");
			FieldValidationUtil.validateMaxSize(productUpdateData.getProductDescription(), 500, "Product Description");
			product.setProductDescription(productUpdateData.getProductDescription());
		}

		if (productUpdateData.getPrice() != null) {
			
			// validate the product price
			FieldValidationUtil.validateMinValue(productUpdateData.getPrice(), 1, "Price");
			product.setPrice(productUpdateData.getPrice());
		}

		if (productUpdateData.getImage() != null) {
			
			// validate product image link
			FieldValidationUtil.validateMinSize(productUpdateData.getImage(), 3, "Product image Link");
			product.setImage(productUpdateData.getImage());
			
		}

		if (productUpdateData.getStocks() != null) {
			
			// validate product stocks 
			FieldValidationUtil.validateMinValue(productUpdateData.getStocks(), 1, "Product Stocks");
			product.setStocks(product.getStocks()+productUpdateData.getStocks());
		}
		
		productRepo.save(product);
		

	}

	@Override
	public Long totalUsers() {
		
		return customerRepo.countUsers();
		
	}

}
