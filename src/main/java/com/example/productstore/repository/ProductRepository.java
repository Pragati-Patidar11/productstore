package com.example.productstore.repository;
import com.example.productstore.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface ProductRepository extends JpaRepository<Product, Long> {

        List<Product> findByPriceGreaterThan(double price);

        List<Product> findByCategory_NameIgnoreCase(String categoryName);

     List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findAllByOrderByPriceDesc(Pageable pageable);

    default List<Product> findTopNExpensiveProducts(Pageable pageable) {
        return findAllByOrderByPriceDesc(pageable);
    }

}

