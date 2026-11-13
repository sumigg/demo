package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
@Schema(description = "A product managed by the API")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 2000)
    private String description;

    protected Product() {
    }

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void updateDetails(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Schema(description = "Unique ID assigned by the API", example = "1")
    public Long getProductId() {
        return productId;
    }

    @Schema(description = "Product name", example = "Notebook")
    public String getName() {
        return name;
    }

    @Schema(description = "Product description", example = "A lined notebook")
    public String getDescription() {
        return description;
    }
}
