package com.app.repo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.model.Product;
import com.app.model.Review;

public interface ProductRepo extends JpaRepository<Product, Long>{
	
	@Query("SELECT p FROM Product p WHERE p.category.cId = :cId")
    Page<Product> findProductsByCategoryId(@Param("cId") Long cId, Pageable pageable);
	
	/**
	 * 
	 * @param key -> product search key
	 * @param pageable -> contains result page information such as number records page no
	 * @return -> page of product
	 */
	@Query("SELECT p FROM Product p WHERE p.productName LIKE %:key%")
	Page<Product> searchProductByKey(@Param("key") String key, Pageable pageable);
	
	/**
	 * 
	 * @param key -> product search key
	 * @param pageable -> contains result page information such as number records page no
	 * @return -> page of product
	 */
	@Query("from Product p where p.productName like concat('%', :key, '%') order by p.price asc")
    Page<Product> searchProductByKeyAndSortByPriceAsc(@Param("key") String key, Pageable pageable);
	
	/**
	 * 
	 * @param key -> product search key
	 * @param pageable -> contains result page information such as number records page no
	 * @return -> page of product
	 */
    @Query("from Product p where p.productName like concat('%', :key, '%') order by p.price desc")
    Page<Product> searchProductByKeyAndSortByPriceDesc(@Param("key") String key, Pageable pageable);
    
    /**
     * 
     * @param key -> product search key
     * @param minPrice -> min price of product
     * @param maxPrice -> max price of product
     * @param pageable -> contains result page information such as number records page no
     * @return -> page of product
     */
    @Query("from Product p where p.productName like concat('%', :key, '%') and p.price >= :minPrice and p.price <= :maxPrice")
    Page<Product> searchProductByKeyAndFilterByMinAndMaxPrice(@Param("key") String key, @Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice, Pageable pageable);
    
    /**
     * 
     * @param key -> product search key
     * @param rating -> product rating
     * @param pageable -> contains result page information such as number records page no
     * @return -> page of product
     */
    @Query("from Product p where p.productName like concat('%', :key, '%') and p.rating >= :rating")
    Page<Product> searchProductByKeyAndFilterByProductRating(@Param("key") String key, @Param("rating") int rating, Pageable pageable);
    
    // 
    @Query("SELECT r FROM Product p JOIN p.reviews r WHERE p.id = :pId")
    Page<Review> findReviewsByProductId(@Param("pId") Long pId, Pageable pageable);
}