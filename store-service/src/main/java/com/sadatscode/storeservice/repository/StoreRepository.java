package com.sadatscode.storeservice.repository;

import com.sadatscode.storeservice.model.Store;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store,String> {

    Optional<Store> findByStoreByBusinessUser(String storeByBusinessUser);
    Optional<Store> findByStoreName(String storeName);
    @Modifying
    @Transactional
    @Query("""
    delete from Store st where st.storeName= :storeName
    """)
    void deleteStoreByStoreName(String storeName);

   @Modifying
   @Transactional
   @Query("""
    delete from Store st where st.storeByBusinessUser= :storeByBusinessUser
    """)
    void deleteStoreByStoreByBusinessUser(String storeByBusinessUser);
}
