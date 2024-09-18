package com.sadatscode.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequest {
    private Long productId;
    private String productName;
    private String productDescription;
    private int productPrice;
    private int productQuantity;
    private String productCategory;
    private String productCurrency;
    private Boolean isInStock;
    private String storeName;
}
