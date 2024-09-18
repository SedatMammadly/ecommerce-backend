package com.sadatscode.securityservice.repository;

import com.sadatscode.securityservice.model.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser,Long> {
    Optional<LoginUser> findByEmail(String email);
}
