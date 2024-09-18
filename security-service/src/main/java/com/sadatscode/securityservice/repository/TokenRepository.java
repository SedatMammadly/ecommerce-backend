package com.sadatscode.securityservice.repository;

import com.sadatscode.securityservice.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("""
       select t from Token t inner join LoginUser u on u.id = t.user.id where u.id = :id and (t.revoked=false and t.expired=false)
"""
    )

    List<Token> findAllValidTokenByUserId( Long id);

    @Query("""
       select t from Token t where t.token = :token and (t.revoked=false and t.expired=false)
""")

    Optional<Token> findByToken(String token);

    @Query("""
       select t.token from Token t where t.user.id = :id and (t.revoked=false and t.expired=false)
""")
   Optional<String>findByUserId(Long id);

}
