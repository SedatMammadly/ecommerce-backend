package com.sadatscode.orderservice.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class OrderItemsDto {
    private Long productId;
    private String productName;
    private String productDescription;
    private int productPrice;
    private int productQuantity;
    private String productCategory;
    private Boolean isInStock;
    private String productCurrency;
    private String storeName;

}
