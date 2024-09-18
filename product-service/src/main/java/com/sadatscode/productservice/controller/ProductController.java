package com.sadatscode.productservice.controller;

import com.sadatscode.productservice.model.Product;
import com.sadatscode.productservice.model.ProductRequest;
import com.sadatscode.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuthForProductService")
public class ProductController {
    private final ProductService service;

    @GetMapping("/all-products")
    public List<Product> getAllProducts() {
       return service.allProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(service.getProductById(productId));
    }

    @GetMapping("/getStoreName/{storeName}")
    public List<Product> getAllProductsByStoreName(@PathVariable String storeName) {
        return service.getAllProductsByStoreName(storeName);
    }

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody ProductRequest request) {
        service.addProduct(request);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductRequest request,@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(service.updateProduct(request,productId));
    }

   @PutMapping("/updateProductQuantity")
    public List<Product> updateProduct(@RequestBody List<ProductRequest> request) {
     return  service.updateProductQuantity(request);
   }

    @DeleteMapping("/delete/{productId}")
    public void deleteProductByProductId(@PathVariable("productId") Long productId) {
        service.deleteProductByProductId(productId);
    }
    @DeleteMapping("/deleteStoreName/{storeName}")
    public void deleteProductByStoreName(@PathVariable("storeName") String storeName) {
        service.deleteProductByStoreName(storeName);
    }
}
