package com.example.productstore.dto;

import com.example.productstore.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryResponse {
    private Long id;
    private String name;
    private List<ProductResponse> products = new ArrayList<>();

    public CategoryResponse() {
    }

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }
}


