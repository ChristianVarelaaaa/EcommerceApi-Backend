package com.ws101.varela.unay.EcommerceApi.controller;

import com.ws101.varela.unay.EcommerceApi.dto.CreateProductDto;
import com.ws101.varela.unay.EcommerceApi.model.Product;
import com.ws101.varela.unay.EcommerceApi.service.ProductService;
import jakarta.validation.Valid;
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

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    //@PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public Product createProduct(@Valid @RequestBody CreateProductDto productDto) {
        // Convert DTO to Entity
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStockQuantity());
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody CreateProductDto productDto) {
        Product productDetails = new Product();
        productDetails.setName(productDto.getName());
        productDetails.setDescription(productDto.getDescription());
        productDetails.setPrice(productDto.getPrice());
        productDetails.setStockQuantity(productDto.getStockQuantity());
        return productService.updateProduct(id, productDetails);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

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