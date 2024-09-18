package com.sadatscode.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    private String customerName;
    private Long customerId;
    private String orderPlace;
    private LocalDate deliveryDate;
    private Boolean completed;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItems> orderItems;

}
