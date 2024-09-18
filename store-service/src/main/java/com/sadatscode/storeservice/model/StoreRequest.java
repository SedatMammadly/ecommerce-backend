package com.sadatscode.storeservice.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StoreRequest {
    private String storeName;
    private int storeCapacity;
    private String storeAddress;
    private String storeCity;
    private String storeByBusinessUser;
}
