package com.sadatscode.productservice.repository;

import com.sadatscode.productservice.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStoreName(String storeName);
   @Modifying
   @Transactional
   @Query("""
      delete from Product p where p.storeName = :storeName
    """)
    void deleteByStoreName(String storeName);
}
