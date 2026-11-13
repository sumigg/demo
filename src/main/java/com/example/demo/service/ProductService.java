package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.ProductRequest;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAllByOrderByProductIdAsc();
    }

    public Optional<Product> findById(long productId) {
        return productRepository.findById(productId);
    }

    public Product create(ProductRequest request) {
        return productRepository.save(new Product(request.name(), request.description()));
    }

    public Optional<Product> update(long productId, ProductRequest request) {
        return productRepository.findById(productId)
                .map(product -> {
                    product.updateDetails(request.name(), request.description());
                    return productRepository.save(product);
                });
    }

    public boolean delete(long productId) {
        if (!productRepository.existsById(productId)) {
            return false;
        }
        productRepository.deleteById(productId);
        return true;
    }
}
