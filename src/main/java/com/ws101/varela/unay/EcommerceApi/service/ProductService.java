package com.ws101.varela.unay.EcommerceApi.service;

import com.ws101.varela.unay.EcommerceApi.model.Product;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    private List<Product> productList = new ArrayList<>();
    private Long nextId = 1L;

    public ProductService() {
        // Add sample data para may laman agad
        productList.add(new Product(nextId++, "Laptop", 45000.0, 10));
        productList.add(new Product(nextId++, "Mouse", 500.0, 50));
    }

    public List<Product> getAllProducts() {
        return productList;
    }

    public Optional<Product> getProductById(Long id) {
        return productList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public Product createProduct(Product product) {
        product.setId(nextId++);
        productList.add(product);
        return product;
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId().equals(id)) {
                updatedProduct.setId(id);
                productList.set(i, updatedProduct);
                return updatedProduct;
            }
        }
        return null;
    }

    public boolean deleteProduct(Long id) {
        return productList.removeIf(p -> p.getId().equals(id));
    }
}