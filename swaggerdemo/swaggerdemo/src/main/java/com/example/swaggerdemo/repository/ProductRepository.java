package com.example.swaggerdemo.repository;

import com.example.swaggerdemo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}


