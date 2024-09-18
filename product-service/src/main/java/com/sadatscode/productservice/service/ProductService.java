package com.sadatscode.productservice.service;

import com.sadatscode.productservice.model.Product;
import com.sadatscode.productservice.model.ProductRequest;
import com.sadatscode.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final WebClient webClient;

    public List<Product> allProducts() {
        return repository.findAll();
    }

    public List<Product> getAllProductsByStoreName(String storeName) {
        return repository.findByStoreName(storeName);
    }

    public void addProduct(ProductRequest request) {
        int storeCapacity = webClient
                .get()
                .uri("http://store-service/api/v1/store/getCapacityByStoreName/" + request.getStoreName())
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
           if (storeCapacity == request.getProductId() || storeCapacity<request.getProductId()) {
               new RuntimeException("max store capacity");
           }
           else{
               Product product = Product.builder()
                       .productName(request.getProductName())
                       .productPrice(request.getProductPrice())
                       .productDescription(request.getProductDescription())
                       .productCategory(request.getProductCategory())
                       .productQuantity(request.getProductQuantity())
                       .isInStock(request.getProductQuantity() > 0)
                       .productCurrency(request.getProductCurrency())
                       .storeName(request.getStoreName())
                       .build();
                 repository.save(product);
           }
    }

    public Product updateProduct(ProductRequest request,Long productId) {
        Product product = repository.findById(productId).orElseThrow(()-> new RuntimeException("Product not found"));
        product.setProductName(request.getProductName());
        product.setProductPrice(request.getProductPrice());
        product.setProductDescription(request.getProductDescription());
        product.setProductCategory(request.getProductCategory());
        product.setProductQuantity(request.getProductQuantity());
        product.setProductCurrency(request.getProductCurrency());
        product.setStoreName(request.getStoreName());
        repository.save(product);
        return product;
    }

    public Product getProductById(Long productId) {
        return repository.findById(productId).orElseThrow(()-> new RuntimeException("Product not found"));
    }

    public List<Product> updateProductQuantity(List<ProductRequest> request) {
       List<Long> orderProductId = request.stream().map(ProductRequest::getProductId).toList();
       List<Product> product =  repository.findAllById(orderProductId);
       if(product.stream().noneMatch(p -> p.getProductQuantity() == 0)){
           product.forEach(products -> {
               products.setProductQuantity(products.getProductQuantity() - 1);
               products.setIsInStock(true);
           });
           product.forEach(item -> item.setIsInStock(false));
       }


       return repository.saveAll(product);
    }

    public void deleteProductByProductId( Long productId) {
        repository.deleteById(productId);
    }

    public void deleteProductByStoreName(String storeName) {
        repository.deleteByStoreName(storeName);
    }
}
