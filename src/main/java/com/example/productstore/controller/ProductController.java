package com.example.productstore.controller;

import com.example.productstore.dto.ProductRequest;
import com.example.productstore.dto.ProductResponse;
import com.example.productstore.exception.ResourceNotFoundException;
import com.example.productstore.model.Product;
import com.example.productstore.repository.ProductRepository;
import com.example.productstore.service.ProductService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")

public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse savedProduct = productService.saveProduct(request);

        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);

    }


    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));


        ProductResponse response = new ProductResponse(product);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        throw new ResourceNotFoundException("Product not found with id: " + id);
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/expensive")
    public ResponseEntity<List<ProductResponse>> getProductsByPriceGreaterThan(@RequestParam double minPrice) {
        List<Product> products = productService.getProductsByPriceGreaterThan(minPrice);
        List<ProductResponse> dtoList = products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }


    @GetMapping("/me")
    public String currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return "Logged in as: " + userDetails.getUsername();
    }



    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {

        Product updatedProduct = productService.updateProduct(id, productRequest)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return ResponseEntity.ok(new ProductResponse(updatedProduct));
    }



    @GetMapping("/category")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@RequestParam String category) {
        List<Product> products = productService.getProductsByCategory(category);
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }



    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String name) {
        List<Product> products = productService.searchProductsByName(name);
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }




    @GetMapping("/by-category-id/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/sorted-by-price")
    public List<ProductResponse> getProductsSortedByPrice(@RequestParam("order") String order) {
        List<Product> products = productService.getProductsSortedByPrice(order);
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());

        return response;

    }



    @GetMapping("/top-expensive")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public  List<ProductResponse> getTopExpensiveProducts(@RequestParam(defaultValue = "5") int limit) {
        List<Product> products = productService.getTopExpensiveProducts(limit);
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());

        return response;

    }

    }
