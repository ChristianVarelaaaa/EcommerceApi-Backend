package com.ws101.varela.unay.EcommerceApi.controller;

import com.ws101.varela.unay.EcommerceApi.model.Product;
import com.ws101.varela.unay.EcommerceApi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // GET ALL - GET /api/products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK); // 200
    }

    // GET ONE - GET /api/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK)) // 200
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 404
    }

    // CREATE - POST /api/products
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED); // 201
    }

    // UPDATE - PUT /api/products/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updated = productService.updateProduct(id, product);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK); // 200
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
    }

    // DELETE - DELETE /api/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
    }
}