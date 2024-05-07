package com.example.products.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.products.model.CommonResponse;
import com.example.products.model.DatavalidationException;
import com.example.products.model.ProductsRequest;
import com.example.products.model.Response;
import com.example.products.model.UpdateProductRequest;
import com.example.products.service.ProductService;

@RequestMapping("products/v1")
@RestController
public class ProductController {

	@Autowired
	private ProductService productservice;

	@PostMapping("create/product")
	public ResponseEntity<CommonResponse> createProduct(@RequestBody ProductsRequest productrequest) {
		try {
			CommonResponse response = productservice.createProducts(productrequest);
			return ResponseEntity.ok(response);
		} catch (DatavalidationException e) {
			return ResponseEntity.badRequest().body(new CommonResponse(HttpStatus.BAD_REQUEST,
					new Response("CreateProductRespone", new ArrayList<>()), e.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CommonResponse(HttpStatus.BAD_REQUEST,
					new Response("CreateProductRespone", new ArrayList<>()), "Error while creating products"));
		}

	}

	@GetMapping("get/all/product")
	public ResponseEntity<CommonResponse> getAllProductsOrById(
			@RequestParam(value = "productId", required = false) String productId) {
		try {
			CommonResponse response = productservice.getAllProductsOrById(productId);
			return ResponseEntity.ok(response);
		} catch (DatavalidationException e) {
			return ResponseEntity.badRequest().body(new CommonResponse(HttpStatus.BAD_REQUEST,
					new Response("getAllprodcutResponse", new ArrayList<>()), e.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CommonResponse(HttpStatus.BAD_REQUEST,
					new Response("getAllprodcutResponse", new ArrayList<>()), "Error while Fectching all products"));
		}

	}

	@PutMapping("update/byproductid")
	public ResponseEntity<CommonResponse> updateProducstById(@RequestParam(required = true) int productId,
			@RequestBody UpdateProductRequest product) {
		try {
			CommonResponse response = productservice.updateProducstById(productId, product);
			return ResponseEntity.ok(response);
		} catch (DatavalidationException e) {
			return ResponseEntity.badRequest().body(new CommonResponse(HttpStatus.BAD_REQUEST,
					new Response("UpdateProductResponse", new ArrayList<>()), e.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CommonResponse(HttpStatus.BAD_REQUEST,
					new Response("UpdateProductResponse", new ArrayList<>()), "Error while updating products"));
		}

	}

	@DeleteMapping("detele/byproductid")
	public ResponseEntity<CommonResponse> deleteByProductId(@RequestParam int productId) {
		try {
			CommonResponse response = productservice.deleteByProductId(productId);
			return ResponseEntity.ok(response);
		} catch (DatavalidationException e) {
			return ResponseEntity.badRequest().body(new CommonResponse(HttpStatus.BAD_REQUEST,
					new Response("DeleteproductResponse", new ArrayList<>()), e.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CommonResponse(HttpStatus.BAD_REQUEST,
					new Response("DeleteproductResponse", new ArrayList<>()), e.getMessage()));
		}

	}

	@GetMapping("search/product")
	public ResponseEntity<CommonResponse> searchProduct(@RequestParam(required = true) String searchText,
			@RequestParam(required = true) String searchBy) {
		try {
			CommonResponse response = productservice.searchProduct(searchText, searchBy);
			return ResponseEntity.ok(response);
		} catch (DatavalidationException e) {
			return ResponseEntity.badRequest().body(new CommonResponse(HttpStatus.BAD_REQUEST,
					new Response("SearchProductResponse", new ArrayList<>()), e.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new CommonResponse(HttpStatus.BAD_REQUEST,
					new Response("SearchProductResponse", new ArrayList<>()), "Error while searching products"));
		}

	}

}
