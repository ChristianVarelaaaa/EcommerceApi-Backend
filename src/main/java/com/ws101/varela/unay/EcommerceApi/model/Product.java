package com.ws101.varela.unay.EcommerceApi.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long id;              // Unique identifier (ID)
    private String name;          // Product name
    private String description;   // Description
    private double price;         // Price
    private String category;      // Category
    private int stock;            // Stock quantity
    private String imageUrl;      // Image URL (optional)
}