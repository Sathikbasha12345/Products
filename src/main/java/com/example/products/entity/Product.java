package com.example.products.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Products")
@Entity
@Data
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCTS_ID")
	private int productId;

	@Nonnull
	@Column(name = "TITLE")
	private String title;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "DUE_DATE")
	// @JsonFormat(shape = @JsonFormat.Shape.String, pattern = "yyyy-mm-dd")
	private Date dueDate;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "CREATED_DATE")
	// @JsonFormat(shape = @JsonFormat.Shape.String, pattern = "yyyy-mm-dd")
	private Date createdDate;

}
