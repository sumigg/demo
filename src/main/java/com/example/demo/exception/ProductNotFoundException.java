package com.example.demo.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(long productId) {
        super("Product with ID " + productId + " was not found.");
    }
}
