package com.ws101.varela.unay.EcommerceApi.service;

import com.ws101.varela.unay.EcommerceApi.model.Product;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    
    private List<Product> productList = new ArrayList<>();
    private Long nextId = 1L;

    public ProductService() {
        // 1. Use List<Product> 
        // 2. Initialize with at least 10 products 
        productList.add(new Product(nextId++, "Gaming Laptop", "RTX 4060, 16GB RAM, 512GB SSD", 65000.0, "Electronics", 15, "https://example.com/laptop1.jpg"));
        productList.add(new Product(nextId++, "Wireless Mouse", "Ergonomic optical mouse with RGB", 1200.0, "Accessories", 50, "https://example.com/mouse1.jpg"));
        productList.add(new Product(nextId++, "Mechanical Keyboard", "Blue switches, full size", 3500.0, "Accessories", 30, "https://example.com/keyboard.jpg"));
        productList.add(new Product(nextId++, "4K Monitor", "27 inch IPS 144Hz", 18000.0, "Electronics", 20, "https://example.com/monitor.jpg"));
        productList.add(new Product(nextId++, "USB-C Hub", "7-in-1 with HDMI and PD", 2500.0, "Accessories", 40, "https://example.com/hub.jpg"));
        productList.add(new Product(nextId++, "Webcam HD", "1080p with microphone", 2200.0, "Electronics", 25, "https://example.com/webcam.jpg"));
        productList.add(new Product(nextId++, "Gaming Chair", "Ergonomic with lumbar support", 12000.0, "Furniture", 10, "https://example.com/chair.jpg"));
        productList.add(new Product(nextId++, "Desk Mat", "XXL mouse pad", 800.0, "Accessories", 60, "https://example.com/mat.jpg"));
        productList.add(new Product(nextId++, "Smartphone", "128GB, 6.5 inch display", 25000.0, "Electronics", 35, "https://example.com/phone.jpg"));
        productList.add(new Product(nextId++, "Bluetooth Speaker", "Waterproof portable speaker", 3200.0, "Audio", 45, "https://example.com/speaker.jpg"));
    }

    // 3.1 ID Generation Strategy 
    
    // 3. Implement methods for:
    // Retrieving all products 
    public List<Product> getAllProducts() {
        return productList;
    }

    // Finding a product by ID 
    public Optional<Product> getProductById(Long id) {
        return productList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    // Creating a new product 
    public Product createProduct(Product product) {
        product.setId(nextId++);
        productList.add(product);
        return product;
    }

    // Updating an existing product 
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

    // Deleting a product 
    public boolean deleteProduct(Long id) {
        return productList.removeIf(p -> p.getId().equals(id));
    }

    
    public List<Product> getProductsByCategory(String category) {
        return productList.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByName(String name) {
        return productList.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByPriceRange(double min, double max) {
        return productList.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }
}
