package com.example.products.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.products.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query(value = "select * from Products a where a.products_Id=:productId", nativeQuery = true)
	Product findByProductId(int productId);

	@Query(value = "select * from Products a where a.title=:productTitle", nativeQuery = true)
	Product findByProductTitle(String productTitle);

}
