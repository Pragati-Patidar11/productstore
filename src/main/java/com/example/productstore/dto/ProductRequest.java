package com.example.productstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ProductRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Price is required")
    private Double price;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Product code is required")
    private String productCode;

    private String imageUrl;

    @NotNull(message = "Release date is required")
    private LocalDate releaseDate;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    public Long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }


    public String getProductCode() {
        return productCode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }
}
