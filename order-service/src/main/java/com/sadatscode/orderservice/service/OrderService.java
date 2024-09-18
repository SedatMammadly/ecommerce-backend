package com.sadatscode.orderservice.service;
import com.sadatscode.orderservice.dto.OrderItemsDto;
import com.sadatscode.orderservice.dto.OrderRequestDto;
import com.sadatscode.orderservice.model.Order;
import com.sadatscode.orderservice.model.OrderItems;
import com.sadatscode.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final WebClient  webClient;


    public Order createOrder(OrderRequestDto requestDto) {
        String token = webClient.get()
                .uri("http://localhost:8081/api/v1/auth/getToken/" + requestDto.getCustomerId())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String CustomerName= webClient.get()
                .uri("http://localhost:8081/api/v1/auth/authenticatedUser")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        LocalDate deliveryDate = LocalDate.now().plusDays(10);
        List<OrderItems> orderItems = requestDto.getOrderItemsDto().stream().map(this::getOrderItems).toList();
        Order order = Order.builder()
                .customerName(CustomerName)
                .customerId(requestDto.getCustomerId())
                .orderItems(orderItems)
                .completed(true)
                .orderPlace(requestDto.getOrderPlace())
                .deliveryDate(deliveryDate)
                .build();


        List<OrderItemsDto> orderItemsDto = requestDto.getOrderItemsDto().stream().toList();
        if (order.getCompleted()) {
            webClient.put()
                    .uri("http://localhost:8083/api/v1/product/updateProductQuantity")
                    .body(BodyInserters.fromValue(orderItemsDto))
                    .retrieve()
                    .bodyToFlux(OrderItemsDto.class)
                    .subscribe(
                            response -> {
                                System.out.println("Product quantity" + response.getProductQuantity());
                            },
                            error -> {
                                System.out.println("Error " + error.getMessage());
                            }
                    );

        }
        return repository.save(order);
    }

    public OrderItems getOrderItems(OrderItemsDto orderItemsDto) {
        return OrderItems.builder()
             .isInStock(orderItemsDto.getIsInStock())
             .productCategory(orderItemsDto.getProductCategory())
             .productName(orderItemsDto.getProductName())
             .productPrice(orderItemsDto.getProductPrice())
             .productQuantity(orderItemsDto.getProductQuantity())
             .productDescription(orderItemsDto.getProductDescription())
             .productCurrency(orderItemsDto.getProductCurrency())
             .storeName(orderItemsDto.getStoreName())
             .build();
    }

    public void deleteOrder(Long orderId) {
        repository.deleteById(orderId);
    }

    public List<Order> getOrderByCustomerName(String customerName) {
       return repository.findByCustomerName(customerName);
    }

    public List<Order> getOrders() {
        return repository.findAll();
    }
}
