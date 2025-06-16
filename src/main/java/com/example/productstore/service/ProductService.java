package com.example.productstore.service;

import com.example.productstore.dto.ProductRequest;
import com.example.productstore.dto.ProductResponse;
import com.example.productstore.model.Category;
import com.example.productstore.model.Product;
import com.example.productstore.repository.CategoryRepository;
import com.example.productstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;



    public ProductResponse saveProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + request.getCategoryId()));

        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setProductCode(request.getProductCode());
        product.setImageUrl(request.getImageUrl());
        product.setReleaseDate(request.getReleaseDate());
        product.setCategory(category);

        Product saved = productRepository.save(product);

        return new ProductResponse(saved);
    }




    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }


    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Product> getProductsByPriceGreaterThan(double price) {
        return productRepository.findByPriceGreaterThan(price);
    }

    public Optional<Product> updateProduct(Long id, ProductRequest request) {
        return productRepository.findById(id).map(existingProduct -> {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            existingProduct.setName(request.getName());
            existingProduct.setDescription(request.getDescription());
            existingProduct.setPrice(request.getPrice());
            existingProduct.setCategory(category);

            return productRepository.save(existingProduct);
        });
    }



    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory_NameIgnoreCase(category);
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }


    public List<Product> getProductsSortedByPrice(String order) {
        Sort sort = order.equalsIgnoreCase("desc") ? Sort.by("price").descending() : Sort.by("price").ascending();
        return productRepository.findAll(sort);
    }



    public List<Product> getTopExpensiveProducts(int limit) {
        return productRepository.findTopNExpensiveProducts(PageRequest.of(0, limit, Sort.by("price").descending()));
    }


}


