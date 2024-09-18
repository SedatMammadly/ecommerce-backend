package com.sadatscode.storeservice.service;

import com.sadatscode.storeservice.model.Store;
import com.sadatscode.storeservice.model.StoreRequest;
import com.sadatscode.storeservice.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository repository;
    private final WebClient webClient;

    public List<Store> allStore() {
        return repository.findAll();
    }

    public Store getStoreById(String id){
        return repository.findById(id).orElseThrow(()-> new RuntimeException("Store not found"));
    }
    public int getCapacityByStoreName(String storeName){
         Store store=repository.findByStoreName(storeName).orElseThrow(()-> new RuntimeException("Store not found"));
         return store.getStoreCapacity();
    }

    public Store createStore(StoreRequest request){

        Store store = Store.builder()
                .storeAddress(request.getStoreAddress())
                .storeName(request.getStoreName())
                .storeCapacity(request.getStoreCapacity())
                .storeCity(request.getStoreCity())
                .storeByBusinessUser(request.getStoreByBusinessUser())
                .build();
        repository.save(store);
        return  store;
    }

    public Store updateStore(StoreRequest request, String storeId) throws Exception {
        Store store = repository.findById(storeId).orElseThrow(()->new Exception("Store not found"));
        store.setStoreName(request.getStoreName());
        store.setStoreCapacity(request.getStoreCapacity());
        store.setStoreCity(request.getStoreCity());
        store.setStoreByBusinessUser(request.getStoreByBusinessUser());
        return repository.save(store);
    }


    public Store getStoreByBusinessUser(String storeByBusinessUser) {
        return repository.findByStoreByBusinessUser(storeByBusinessUser).orElseThrow(()-> new RuntimeException("store not found"));
    }

    public void deleteStore(String storeId) {
        repository.deleteById(storeId);
    }
    @Transactional
    public void deleteStoreByStoreName(String storeName) {
        repository.deleteStoreByStoreName(storeName);
        webClient.delete()
                .uri("http://localhost:8083/api/v1/product/deleteStoreName/"+ storeName)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
   @Transactional
    public void deleteStoreByBusinessUser(String storeByBusinessUser) {
        Store store = repository.findByStoreByBusinessUser(storeByBusinessUser).orElseThrow(()-> new RuntimeException("store not found"));
        webClient.delete()
                .uri("http://localhost:8083/api/v1/product/deleteStoreName/"+ store.getStoreName())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
        repository.deleteStoreByStoreByBusinessUser(storeByBusinessUser);
    }
}
