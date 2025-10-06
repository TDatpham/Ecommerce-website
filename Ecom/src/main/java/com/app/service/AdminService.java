package com.app.service;

import com.app.dtos.ProductUpdateData;
import com.app.exceptions.AdminException;
import com.app.exceptions.CategoryException;
import com.app.exceptions.ProductException;
import com.app.model.Admin;
import com.app.model.Category;
import com.app.model.Product;

public interface AdminService {
	
	/**
	 * 
	 * @param admin -> admin data for admin registration
	 * @return -> registration message
	 * @throws AdminException
	 */
	String registerAdmin(Admin admin)throws AdminException;
	
	/**
	 * 
	 * @param category -> Product category to add
	 * @return -> added category 
	 */
	Category addCategory(Category category);
	
	/**
	 * 
	 * @param categoryId -> to find category
	 * @param product -> to add to category with category id
	 * @return -> added product 
	 * @throws ProductException
	 * @throws CategoryException
	 */
	Product addProduct(Long categoryId, Product product)throws ProductException, CategoryException;
	
	/**
	 * 
	 * @param productUpdateData -> contains product data for update 
	 * @throws ProductException
	 */
	void updateProduct(ProductUpdateData productUpdateData)throws ProductException;
	
	/**
	 * 
	 * @return -> count of total customers 
	 */
	Long totalUsers();
	
	
}
