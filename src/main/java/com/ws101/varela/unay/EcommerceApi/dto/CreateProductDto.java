package com.ws101.varela.unay.EcommerceApi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateProductDto {

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 100, message = "Name must be 3-100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description max 500 characters")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Stock is required")
    @Positive(message = "Stock must be positive")
    private Integer stock;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}