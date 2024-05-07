package com.example.products.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_NULL)
@Data
public class UpdateProductRequest {

	private String title;
	private String description;
	private Date dueDate;
	private String status;

}
