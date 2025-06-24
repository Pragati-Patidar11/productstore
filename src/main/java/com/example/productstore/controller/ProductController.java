package com.example.productstore.controller;

import com.example.productstore.dto.ProductRequest;
import com.example.productstore.dto.ProductResponse;
import com.example.productstore.exception.ResourceNotFoundException;
import com.example.productstore.model.Product;
import com.example.productstore.repository.ProductRepository;
import com.example.productstore.service.ProductService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    @GetMapping("/paginated")
    public ResponseEntity<Page<ProductResponse>> getPaginatedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy
    ) {
        Page<ProductResponse> response = productService.getPaginatedProducts(page, size, sortBy);
        return ResponseEntity.ok(response);
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

    @GetMapping("/expensive-paginated")
    public Page<ProductResponse> getProductsByMinPrice(@RequestParam double minPrice,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        return productService.getProductsByMinPricePaginated(minPrice, page, size);
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

    @GetMapping("/by-category-paginated")
    public Page<ProductResponse> getProductsByCategory(@RequestParam String category,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        return productService.getProductsByCategoryNamePaginated(category, page, size);
    }


    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String name) {
        List<Product> products = productService.searchProductsByName(name);
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search-paginated")
    public Page<ProductResponse> searchProductsByName(@RequestParam String name,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return productService.searchProductsByNamePaginated(name, page, size);
    }



        @GetMapping("/by-category-id/{categoryId}")
        public ResponseEntity<List<ProductResponse>> getProductsByCategoryId (@PathVariable Long categoryId){
            List<Product> products = productService.getProductsByCategoryId(categoryId);

            if (products.isEmpty()) {
                throw new ResourceNotFoundException("No products found for category ID: " + categoryId);
            }

            List<ProductResponse> response = products.stream()
                    .map(ProductResponse::new)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);
        }


    @GetMapping("/by-category-id-paginated/{id}")
    public Page<ProductResponse> getProductsByCategoryId(@PathVariable Long id,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        return productService.getProductsByCategoryIdPaginated(id, page, size);
    }


        @GetMapping("/sorted-by-price")
        public List<ProductResponse> getProductsSortedByPrice (@RequestParam("order") String order){
            List<Product> products = productService.getProductsSortedByPrice(order);
            List<ProductResponse> response = products.stream()
                    .map(ProductResponse::new)
                    .collect(Collectors.toList());

            return response;

        }

    @GetMapping("/sorted")
    public List<ProductResponse> getSortedProducts(@RequestParam(defaultValue = "price") String sortBy) {
        return productService.getAllProductsSorted(sortBy);
    }

        @GetMapping("/top-expensive")
        @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
        public List<ProductResponse> getTopExpensiveProducts ( @RequestParam(defaultValue = "5") int limit){
            List<Product> products = productService.getTopExpensiveProducts(limit);
            List<ProductResponse> response = products.stream()
                    .map(ProductResponse::new)
                    .collect(Collectors.toList());

            return response;

        }


    }
