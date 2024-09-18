package com.sadatscode.orderservice.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Getter
public class OrderRequestDto {
    private String customerName;
    private Long customerId;
    private String orderPlace;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItemsDto> orderItemsDto;
}
