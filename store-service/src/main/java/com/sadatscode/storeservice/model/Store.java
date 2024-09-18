package com.sadatscode.storeservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "Store")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String storeId;
    @Column(unique = true)
    private String storeName;
    private int storeCapacity;
    private String storeAddress;
    private String storeCity;
    private String storeByBusinessUser;
}
