package com.example.productstore.repository;
import com.example.productstore.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByPriceGreaterThan(double price);

    Page<Product> findByPriceGreaterThanEqual(double price, Pageable pageable);

    List<Product> findByCategory_NameIgnoreCase(String categoryName);

    Page<Product> findByCategory_Name(String name, Pageable pageable);

    List<Product> findByNameContainingIgnoreCase(String name);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Product> findByCategoryId(Long categoryId);

    Page<Product> findByCategory_Id(Long id, Pageable pageable);

    List<Product> findAllByOrderByPriceDesc(Pageable pageable);


}

