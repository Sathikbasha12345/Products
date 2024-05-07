package com.example.products.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.products.entity.Product;
import com.example.products.model.CommonResponse;
import com.example.products.model.DatavalidationException;
import com.example.products.model.ProductListResponse;
import com.example.products.model.ProductsRequest;
import com.example.products.model.Response;
import com.example.products.model.UpdateProductRequest;
import com.example.products.repo.ProductRepository;
import com.example.products.service.ProductService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productrepository;

	@Autowired
	private EntityManager entitymanager;

	@Override
	public CommonResponse createProducts(ProductsRequest productrequest) throws DatavalidationException {

		Product existingproduct = productrepository.findByProductTitle(productrequest.getTitle());
		if (existingproduct != null) {
			throw new DatavalidationException("Title already exist,please provide valid title", 400,
					HttpStatus.BAD_REQUEST);
		}
		Product product = new Product();
		product.setTitle(productrequest.getTitle());
		product.setDescription(productrequest.getDescription());
		product.setStatus(productrequest.getStatus());
		product.setDueDate(productrequest.getDueDate());
		product.setCreatedDate(new Date());
		productrepository.save(product);
		return new CommonResponse(HttpStatus.OK, new Response("createProductResponse", new ArrayList<>()),
				"Product created Successfully");
	}

	@Override
	public CommonResponse getAllProductsOrById(String productId) throws DatavalidationException {
		List<ProductListResponse> listreponse = null;
		if (productId != null) {
			Product product = productrepository.findByProductId(Integer.valueOf(productId));
			if (product != null) {
				return new CommonResponse(HttpStatus.OK, new Response("ListResponse", product),
						"ListResponse fetched Successfully");
			} else {
				throw new DatavalidationException("No product is available", 400, HttpStatus.BAD_REQUEST);
			}
		} else {
			List<Product> list = productrepository.findAll();
			listreponse = list.stream().map(s -> {
				ProductListResponse response = new ProductListResponse();
				response.setTitle(s.getTitle());
				response.setDescription(s.getDescription());
				response.setStatus(s.getStatus());
				response.setDueDate(s.getDueDate());
				return response;
			}).collect(Collectors.toList());
		}
		return new CommonResponse(HttpStatus.OK, new Response("ListResponse", listreponse),
				"ListResponse fetched Successfully");
	}

	@Override
	public CommonResponse updateProducstById(int productId, UpdateProductRequest productrequest)
			throws DatavalidationException {
		Product product = null;
		if (productId == 0) {
			throw new DatavalidationException("please provide valid productID", 400, HttpStatus.BAD_REQUEST);
		} else {
			product = productrepository.findByProductId(productId);
			if (productrequest.getTitle() != null) {
				product.setTitle(productrequest.getTitle());
			}
			if (productrequest.getStatus() != null) {
				product.setStatus(productrequest.getStatus());
			}
			if (productrequest.getDescription() != null) {

				product.setDescription(productrequest.getDescription());
			}
			if (productrequest.getDueDate() != null) {
				product.setDueDate(productrequest.getDueDate());
			}
		}
		productrepository.save(product);
		return new CommonResponse(HttpStatus.OK, new Response("ProductUpdateResponse", new ArrayList<>()),
				"Product updated Successfully");
	}

	@Override
	public CommonResponse deleteByProductId(int productId) throws DatavalidationException {
		Product product = null;
		if (productId == 0) {
			throw new DatavalidationException("please provide valid productID", 400, HttpStatus.BAD_REQUEST);
		}
		product = productrepository.findByProductId(productId);
		if (product != null) {
			productrepository.delete(product);
			return new CommonResponse(HttpStatus.OK, new Response("ProductDeteleResponse", new ArrayList<>()),
					"Product deleted Successfully");
		} else {
			return new CommonResponse(HttpStatus.OK, new Response("ProductDeteleResponse", new ArrayList<>()),
					"No products available");

		}

	}

	@Override
	public CommonResponse searchProduct(String searchText, String searchBy) throws DatavalidationException {
		if (searchText.isEmpty() || searchBy.isEmpty()) {
			throw new DatavalidationException("Please provide searchText or searchBy key", 400, HttpStatus.BAD_REQUEST);
		}
		List<String> keystosearch = Arrays.asList("title", "status");

		if (keystosearch.stream().noneMatch(searchBy::equalsIgnoreCase)) {
			throw new DatavalidationException("please provide valid productID", 400, HttpStatus.BAD_REQUEST);
		}
		List<Product> list = null;
		CriteriaQuery<Product> criteriaQuery = null;
		if (searchText != null && !searchText.isEmpty()) {
			CriteriaBuilder criteriabuilder = entitymanager.getCriteriaBuilder();
			criteriaQuery = criteriabuilder.createQuery(Product.class);
			Root<Product> root = criteriaQuery.from(Product.class);
			criteriaQuery.select(root);
			criteriabuilder.like(criteriabuilder.lower(root.get("title")), "%" + searchText + "%");
			Predicate searchPredicate = null;
			if (searchText != null && !searchText.isEmpty()) {

				if (searchBy.equalsIgnoreCase("title")) {
					searchPredicate = criteriabuilder.like(criteriabuilder.lower(root.get("title")),
							"%" + searchText.toLowerCase() + "%");
				} else if (searchBy.equalsIgnoreCase("status")) {
					searchPredicate = criteriabuilder.like(criteriabuilder.lower(root.get("status")),
							"%" + searchText.toLowerCase() + "%");
				}
			}
			criteriaQuery.where(searchPredicate);
		}
		list = entitymanager.createQuery(criteriaQuery).getResultList();

		if (!list.isEmpty()) {
			return new CommonResponse(HttpStatus.OK, new Response("SearchProductResponse", list),
					"Searched Product Displayed Successfully");
		} else {
			return new CommonResponse(HttpStatus.OK, new Response("SearchProductResponse", new ArrayList<>()),
					"No Searched Product available ");
		}
	}

}
