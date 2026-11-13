package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Product details used when creating or updating a product")
public record ProductRequest(
        @Schema(description = "Product name", example = "Notebook")
        String name,
        @Schema(description = "Product description", example = "A lined notebook")
        String description) {
}
