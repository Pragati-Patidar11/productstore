package com.example.productstore.repository;

import com.example.productstore.model.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    List<Category> findByNameContainingIgnoreCase(String name);

    @EntityGraph(attributePaths = "products")
    Optional<Category> findById(Long id);

}

