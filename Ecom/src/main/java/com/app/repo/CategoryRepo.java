package com.app.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import com.app.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long>{
	
	
}
