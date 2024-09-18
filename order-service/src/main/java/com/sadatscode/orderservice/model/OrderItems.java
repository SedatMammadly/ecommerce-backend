package com.sadatscode.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_line_items")
public class OrderItems {
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
