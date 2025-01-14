package com.example.products.model;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonResponse {
	
   private HttpStatus status;
   private Response response;
   private String message;
}
