package com.example.products.model;

import java.util.Date;

import jakarta.annotation.Nonnull;
import lombok.Data;

@Data
public class ProductsRequest {

	@Nonnull
	private String title;
	@Nonnull
	private String description;
	@Nonnull
	private Date dueDate;
	@Nonnull
	private String status;

}
