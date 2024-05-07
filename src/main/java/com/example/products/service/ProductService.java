package com.example.products.service;

import com.example.products.model.CommonResponse;
import com.example.products.model.DatavalidationException;
import com.example.products.model.ProductsRequest;
import com.example.products.model.UpdateProductRequest;

public interface ProductService {

	CommonResponse createProducts(ProductsRequest productrequest)throws DatavalidationException;

	CommonResponse getAllProductsOrById(String productId) throws DatavalidationException;

	CommonResponse updateProducstById(int productId, UpdateProductRequest product) throws DatavalidationException;

	CommonResponse deleteByProductId(int productId) throws DatavalidationException;

	CommonResponse searchProduct(String searchText, String searchBy) throws DatavalidationException;

}
