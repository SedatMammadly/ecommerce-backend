package com.sadatscode.securityservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1024)
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private Boolean revoked;
    private Boolean expired;
    @ManyToOne
    @JoinColumn(name = "login_user_id")
    private LoginUser user;

}
