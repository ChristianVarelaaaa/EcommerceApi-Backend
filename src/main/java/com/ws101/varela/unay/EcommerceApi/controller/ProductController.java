package com.ws101.varela.unay.EcommerceApi.controller;

import com.ws101.varela.unay.EcommerceApi.model.Product;
import com.ws101.varela.unay.EcommerceApi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products") // Task 4.1: Map to /api/v1/products base path
public class ProductController {

    @Autowired
    private ProductService productService;

    // GET /api/v1/products - Return all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // GET /api/v1/products/{id} - Return single product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/v1/products/filter?filterType=category&filterValue=Electronics
    @GetMapping("/filter")
    public List<Product> filterProducts(
            @RequestParam String filterType,
            @RequestParam String filterValue) {

        switch (filterType.toLowerCase()) {
            case "category":
                return productService.getProductsByCategory(filterValue);
            case "name":
                return productService.getProductsByName(filterValue);
            case "price":
                // Format: min-max e.g., 1000-5000
                String[] range = filterValue.split("-");
                double min = Double.parseDouble(range[0]);
                double max = Double.parseDouble(range[1]);
                return productService.getProductsByPriceRange(min, max);
            default:
                return productService.getAllProducts();
        }
    }

    // POST /api/v1/products - Create new product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product created = productService.createProduct(product);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // PUT /api/v1/products/{id} - Replace entire product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updated = productService.updateProduct(id, product);
        if (updated!= null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // PATCH /api/v1/products/{id} - Partially update product
    @PatchMapping("/{id}")
    public ResponseEntity<Product> partialUpdateProduct(@PathVariable Long id, @RequestBody Product productPatch) {
        Optional<Product> existingProduct = productService.getProductById(id);
        if (existingProduct.isPresent()) {
            Product current = existingProduct.get();

            // Update lang yung fields na hindi null
            if (productPatch.getName()!= null) current.setName(productPatch.getName());
            if (productPatch.getDescription()!= null) current.setDescription(productPatch.getDescription());
            if (productPatch.getPrice()!= 0) current.setPrice(productPatch.getPrice());
            if (productPatch.getCategory()!= null) current.setCategory(productPatch.getCategory());
            if (productPatch.getStock()!= 0) current.setStock(productPatch.getStock());
            if (productPatch.getImageUrl()!= null) current.setImageUrl(productPatch.getImageUrl());

            Product updated = productService.updateProduct(id, current);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/v1/products/{id} - Remove product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
