package com.example.productstore.dto;

import com.example.productstore.model.Product;

import java.time.LocalDate;

public class ProductResponse {
    private Long id;
    private String name;
    private double price;
    private String description;
    private String productCode;
    private String imageUrl;
    private LocalDate releaseDate;
    private String categoryName;

    public ProductResponse() {
    }

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.productCode = product.getProductCode();
        this.imageUrl = product.getImageUrl();
        this.releaseDate = product.getReleaseDate();
        this.categoryName = product.getCategory() != null ? product.getCategory().getName() : null;
    }


    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
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
    public String getCategoryName() {
        return categoryName;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
