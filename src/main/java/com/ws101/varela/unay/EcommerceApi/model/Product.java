package com.ws101.varela.unay.EcommerceApi.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Getter              // Para sa getters
@Setter              // Para sa setters
@ToString            // Para sa toString() method
@EqualsAndHashCode   // Para sa equals() at hashCode()
@AllArgsConstructor  // Constructor with all fields
@NoArgsConstructor   // Empty constructor
public class Product {
    private Long id;              // Unique identifier (ID)
    private String name;          // Product name
    private String description;   // Description
    private double price;         // Price
    private String category;      // Category
    private int stock;            // Stock quantity
    private String imageUrl;      // Image URL (optional)
}
