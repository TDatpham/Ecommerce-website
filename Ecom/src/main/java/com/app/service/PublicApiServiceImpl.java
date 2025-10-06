package com.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dtos.ApiResponse;
import com.app.dtos.ForgotPasswordRequest;
import com.app.dtos.PageInfo;
import com.app.dtos.ResetPasswordRequest;
import com.app.exceptions.CategoryException;
import com.app.exceptions.ProductException;
import com.app.exceptions.ResetPasswordException;
import com.app.model.Admin;
import com.app.model.Category;
import com.app.model.Customer;
import com.app.model.ForgotPasswordOTP;
import com.app.model.Otp;
import com.app.model.Product;
import com.app.model.Review;
import com.app.repo.AdminRepo;
import com.app.repo.CategoryRepo;
import com.app.repo.CustomerRepo;
import com.app.repo.ForgotPasswordOTPRepo;
import com.app.repo.ProductRepo;

@Service
public class PublicApiServiceImpl implements PublicApiService {
	
	@Autowired
	private ProductRepo productRepo; 
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired
	private ForgotPasswordOTPRepo forgotPasswordOTPRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override
	public ApiResponse<Product>  searchProducts(String key, int page) {

		Pageable pageable = PageRequest.of(page, 16); 
	    Page<Product> products = productRepo.searchProductByKey(key, pageable);
	    
	    // Create response object
	    ApiResponse<Product> data = new ApiResponse<>();
	    data.setData(products.getContent());
	    
	    // Create and set PageInfo
	    PageInfo pageInfo = new PageInfo();
	    pageInfo.setCurrentPage(page); 
	    pageInfo.setTotalPages(products.getTotalPages());
	    pageInfo.setTotalRecords((int) products.getTotalElements());
	    pageInfo.setRecordPerPage(products.getSize()); 
	    
	    data.setPageInfo(pageInfo);
	    
	    return data;
	}
	
	@Override
	public ApiResponse<Product> findProductsByCategoryId(Long categoryId, int page)
			throws CategoryException, ProductException {
		
		Optional<Category> optCategory = categoryRepo.findById(categoryId);
		if(optCategory.isEmpty()) {
			throw new CategoryException("Invalid category id");
		}
		
		
		Pageable pageable = PageRequest.of(page, 16);
		Page<Product> products = productRepo.findProductsByCategoryId(categoryId, pageable);
		
		if(products.isEmpty()) {
			throw new ProductException("No product found");
		}
		
		 // Create response object
	    ApiResponse<Product> data = new ApiResponse<>();
	    data.setData(products.getContent());
	    
	    // Create and set PageInfo
	    PageInfo pageInfo = new PageInfo();
	    pageInfo.setCurrentPage(page); 
	    pageInfo.setTotalPages(products.getTotalPages());
	    pageInfo.setTotalRecords((int) products.getTotalElements());
	    pageInfo.setRecordPerPage(products.getSize()); 
	    
	    data.setPageInfo(pageInfo);
	    
	    return data;
		
	}
	
	@Override
	public List<Category> getCategories() {
		
		Pageable pageable = PageRequest.of(0, 12); 
        return categoryRepo.findAll(pageable).getContent();	
    }

	@Override
	public ApiResponse<Product> searchProductAndFilterByRating(String key, int page, int rating) {

		Pageable pageable = PageRequest.of(page, 16);
		Page<Product> products = productRepo.searchProductByKeyAndFilterByProductRating(key, rating, pageable);
		

		 // Create response object
	    ApiResponse<Product> data = new ApiResponse<>();
	    data.setData(products.getContent());
	    
	    // Create and set PageInfo
	    PageInfo pageInfo = new PageInfo();
	    pageInfo.setCurrentPage(page); 
	    pageInfo.setTotalPages(products.getTotalPages());
	    pageInfo.setTotalRecords((int) products.getTotalElements());
	    pageInfo.setRecordPerPage(products.getSize()); 
	    
	    data.setPageInfo(pageInfo);
	    
	    return data;

	}

	@Override
	public ApiResponse<Product> searchProductAndFilterByMinAndMaxPrice(String key, int page, int minPrice, int maxPrice) {

		Pageable pageable = PageRequest.of(page, 16);
		Page<Product> products = productRepo.searchProductByKeyAndFilterByMinAndMaxPrice(key, minPrice, maxPrice,
				pageable);

		// Create response object
	    ApiResponse<Product> data = new ApiResponse<>();
	    data.setData(products.getContent());
	    
	    // Create and set PageInfo
	    PageInfo pageInfo = new PageInfo();
	    pageInfo.setCurrentPage(page); 
	    pageInfo.setTotalPages(products.getTotalPages());
	    pageInfo.setTotalRecords((int) products.getTotalElements());
	    pageInfo.setRecordPerPage(products.getSize()); 
	    
	    data.setPageInfo(pageInfo);
	    
	    return data;

	}

	@Override
	public ApiResponse<Product> searchProductAndSortByPriceAsc(String key, int page) {

		Pageable pageable = PageRequest.of(page, 16);
		Page<Product> products = productRepo.searchProductByKeyAndSortByPriceAsc(key, pageable);

		// Create response object
	    ApiResponse<Product> data = new ApiResponse<>();
	    data.setData(products.getContent());
	    
	    // Create and set PageInfo
	    PageInfo pageInfo = new PageInfo();
	    pageInfo.setCurrentPage(page); 
	    pageInfo.setTotalPages(products.getTotalPages());
	    pageInfo.setTotalRecords((int) products.getTotalElements());
	    pageInfo.setRecordPerPage(products.getSize()); 
	    
	    data.setPageInfo(pageInfo);
	    
	    return data;

	}

	@Override
	public ApiResponse<Product> searchProductAndSortByPriceDesc(String key, int page) {

		Pageable pageable = PageRequest.of(page, 16);
		Page<Product> products = productRepo.searchProductByKeyAndSortByPriceDesc(key, pageable);

		// Create response object
	    ApiResponse<Product> data = new ApiResponse<>();
	    data.setData(products.getContent());
	    
	    // Create and set PageInfo
	    PageInfo pageInfo = new PageInfo();
	    pageInfo.setCurrentPage(page); 
	    pageInfo.setTotalPages(products.getTotalPages());
	    pageInfo.setTotalRecords((int) products.getTotalElements());
	    pageInfo.setRecordPerPage(products.getSize()); 
	    
	    data.setPageInfo(pageInfo);
	    
	    return data;
	}


	@Override
	public ApiResponse<Review> getReviewsByProduct(Long productId, int page) {
		
		Optional<Product> optProduct = productRepo.findById(productId);
		if(optProduct.isEmpty()) throw new ProductException("Invalid Product Id : "+productId);
		
		
		Pageable pageable = PageRequest.of(page, 10);
		Page<Review> reviews = productRepo.findReviewsByProductId(productId, pageable);
		
		// Create response object
	    ApiResponse<Review> data = new ApiResponse<>();
	    data.setData(reviews.getContent());
	    
	    // Create and set PageInfo
	    PageInfo pageInfo = new PageInfo();
	    pageInfo.setCurrentPage(page); 
	    pageInfo.setTotalPages(reviews.getTotalPages());
	    pageInfo.setTotalRecords((int) reviews.getTotalElements());
	    pageInfo.setRecordPerPage(reviews.getSize()); 
	    
	    data.setPageInfo(pageInfo);
	    
	    return data;
		
	}

	@Override
	public Product getProductById(Long productId) {
		return productRepo.findById(productId)
				.orElseThrow(() -> new ProductException("Invalid Product id : " + productId));
	}

	@Override
	public String forgotPasswordRequest(ForgotPasswordRequest forgotPasswordRequest) {
		
		
		Optional<Customer> optCustomer = customerRepo.findByEmail(forgotPasswordRequest.getEmail());
		Optional<Admin> optAdmin = adminRepo.findByEmail(forgotPasswordRequest.getEmail());
		if(optCustomer.isPresent() || optAdmin.isPresent()) {
			// note ! gotta make below email dynamic
	 		String to = "pawankumarsahu1022@gmail.com";
	 		String from = "kumarpawanm8085@gmail.com";
	 	
	 		String subject = "Reset Password OTP..";
	 		
	 		String otp = getOtp();
	 		
	 		
	 		while(forgotPasswordOTPRepo.findByOtpAndEmail(otp, forgotPasswordRequest.getEmail()).isPresent()){
	 			otp = getOtp();
	 		}
	 		String text = "YOUR OTP : "+otp;
	 		boolean sent =  emailService.send(to, from, subject, text);
	 		
	 	
	 		if(sent) {
	 			ForgotPasswordOTP o = new ForgotPasswordOTP();
	 			
	 			o.setOtp(otp);
	 			o.setEmail(forgotPasswordRequest.getEmail());
	 			o.setExpiryTime(LocalDateTime.now().plusMinutes(5));
	 			forgotPasswordOTPRepo.save(o);
	 			return "OTP sent successfully to the provided email address.";
	 		}
	 		throw new ResetPasswordException("Something went wrong!");
		}
		
		
		throw new ResetPasswordException("Invalid email !");
	}
	
	public boolean isExpired(LocalDateTime expiryTime) {
        return LocalDateTime.now().isAfter(expiryTime); // Check if current time is after expiry time
    }
	
	public String getOtp() {
		Random random = new Random();
		StringBuilder otp = new StringBuilder();
 		otp.append(random.nextInt(10)+"");
 		otp.append(random.nextInt(10)+"");
 		otp.append(random.nextInt(10)+"");
 		otp.append(random.nextInt(10)+"");
 		otp.append(random.nextInt(10)+"");
 		otp.append(random.nextInt(10)+"");
 		return otp.toString();
	}

	@Override
	public String passwordResetRequest(ResetPasswordRequest resetPasswordRequest) throws ResetPasswordException {
		
		Optional<Customer> optCustomer = customerRepo.findByEmail(resetPasswordRequest.getEmail());
		Optional<Admin> optAdmin = adminRepo.findByEmail(resetPasswordRequest.getEmail());
		
		// check email is valid or not 
		if(optCustomer.isEmpty() && optAdmin.isEmpty()) {
			throw new ResetPasswordException("Invalid email!");
		}
		
		Optional<ForgotPasswordOTP> optForgotPass = forgotPasswordOTPRepo.findByOtpAndEmail(resetPasswordRequest.getOtp(), resetPasswordRequest.getEmail());
		
		if(optForgotPass.isPresent()) {
			
			ForgotPasswordOTP forgotPasswordOTP = optForgotPass.get();
			if(isExpired(forgotPasswordOTP.getExpiryTime())) {
				
				// remove otp if it is expired
				forgotPasswordOTPRepo.delete(forgotPasswordOTP);
				throw new ResetPasswordException("Otp has expired!");
			}
			
			
			if(optCustomer.isPresent()) {
				
				Customer customer = customerRepo.findByEmail(resetPasswordRequest.getEmail()).get();
				customer.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
				customerRepo.save(customer);
				return "Your password has been successfully reset";
			}
			if(optAdmin.isPresent()) {
				Admin admin = adminRepo.findByEmail(resetPasswordRequest.getEmail()).get();
				admin.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
				adminRepo.save(admin);
				return "Your password has been successfully reset";
			}
			
			// remove otp from db after successfully reseting password
			forgotPasswordOTPRepo.delete(forgotPasswordOTP);
			
		}
		
		throw new ResetPasswordException("Invalid otp check again in your email");
		
	
		
	}

	

	

}
