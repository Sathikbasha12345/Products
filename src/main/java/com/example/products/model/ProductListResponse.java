package com.example.products.model;

import java.util.Date;

import lombok.Data;

@Data
public class ProductListResponse {

	private String title;
	private String description;
	private Date dueDate;
	private String status;

}
