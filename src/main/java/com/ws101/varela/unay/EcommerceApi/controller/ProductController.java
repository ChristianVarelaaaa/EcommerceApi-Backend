package com.ws101.varela.unay.EcommerceApi.controller;

import com.ws101.varela.unay.EcommerceApi.model.Product;
import com.ws101.varela.unay.EcommerceApi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Public
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Public
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // Seller/Admin
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    // Seller/Admin
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productService.updateProduct(id, productDetails);
    }

    // Admin only
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    // Public
    @GetMapping("/filter")
    public List<Product> filterProducts(
        @RequestParam String filterType,
        @RequestParam String filterValue
    ) {
        switch (filterType.toLowerCase()) {
            case "category":
                return productService.getProductsByCategory(filterValue);
            case "name":
                return productService.searchProductsByName(filterValue);
            case "price":
                String[] range = filterValue.split("-");
                Double min = Double.parseDouble(range[0]);
                Double max = Double.parseDouble(range[1]);
                return productService.getProductsByPriceRange(min, max);
            default:
                return productService.getAllProducts();
        }
    }
}