package com.sadatscode.storeservice.controller;

import com.sadatscode.storeservice.model.Store;
import com.sadatscode.storeservice.model.StoreRequest;
import com.sadatscode.storeservice.repository.StoreRepository;
import com.sadatscode.storeservice.service.StoreService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuthForStoreService")
public class StoreController {
    private final StoreService service;
    private final StoreRepository repository;

    @GetMapping
    public List<Store> allStore(){
        return service.allStore();
    }

    @GetMapping("getBusinessUser/{storeByBusinessUser}")
    public ResponseEntity<Store> getStoreByBusinessUser(@PathVariable String storeByBusinessUser){
        return ResponseEntity.ok(service.getStoreByBusinessUser(storeByBusinessUser));
    }

    @GetMapping("getCapacityByStoreName/{storeName}")
    public ResponseEntity<Integer> getStoreByStoreName(@PathVariable String storeName) {
        return ResponseEntity.ok(service.getCapacityByStoreName(storeName));
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<Store> getStoreById(@PathVariable("storeId") String storeId) throws Exception {
        return ResponseEntity.ok(service.getStoreById(storeId));
    }

    @PostMapping("/createStore")
    public ResponseEntity<Store> createStore(@RequestBody StoreRequest request) {
        return ResponseEntity.ok(service.createStore(request));
    }

    @PutMapping("/updateStore/{storeId}")
    public ResponseEntity<Store> updateStore(@RequestBody StoreRequest request,@PathVariable("storeId") String storeId) throws Exception {
        return ResponseEntity.ok(service.updateStore(request,storeId));
    }

    @DeleteMapping("/{storeId}")
    public void deleteStore(@PathVariable("storeId") String storeId) {
        service.deleteStore(storeId);
    }
    @DeleteMapping("/deleteByStoreName/{storeName}")
    public void deleteStoreByStoreName(@PathVariable("storeName") String storeName) {
        service.deleteStoreByStoreName(storeName);
    }
    @DeleteMapping("/delete/{storeByBusinessUser}")
    public void deleteStoreByBusinessUser(@PathVariable("storeByBusinessUser") String storeByBusinessUser) {
        service.deleteStoreByBusinessUser(storeByBusinessUser);
    }
}
