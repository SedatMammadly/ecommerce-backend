package com.sadatscode.productservice.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
