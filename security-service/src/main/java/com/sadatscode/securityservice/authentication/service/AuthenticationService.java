package com.sadatscode.securityservice.authentication.service;

import com.sadatscode.securityservice.authentication.AuthenticationRequest;
import com.sadatscode.securityservice.authentication.AuthenticationResponse;
import com.sadatscode.securityservice.model.*;
import com.sadatscode.securityservice.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sadatscode.securityservice.repository.LoginUserRepository;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final LoginUserRepository loginUserRepository;
    private final TokenRepository tokenRepository;
    private final WebClient webClient;


    public AuthenticationResponse registerUser(RegisterRequest request) {
        LoginUser user = LoginUser.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .userType(UserType.USER)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
       var savedUser = loginUserRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        savedToken(jwtToken,savedUser);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }


    public AuthenticationResponse registerBusinessUser(RegisterRequest request) {
        LoginUser user2 = LoginUser.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .userType(UserType.BUSINESSUSER)
                .build();
        var savedUser = loginUserRepository.save(user2);
        String jwtToken = jwtService.generateToken(user2);
        savedToken(jwtToken, savedUser);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
        var user = loginUserRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(()->new RuntimeException("User Not Found"));
        String jwtToken = jwtService.generateToken(user);
        revokeToken(user);
        savedToken(jwtToken,user);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }

    private void savedToken(String jwtToken, LoginUser user) {
        Token token = Token.builder()
                .token(jwtToken)
                .user(user)
                .revoked(false)
                .expired(false)
                .tokenType(TokenType.BEARER)
                .build();
        tokenRepository.save(token);
    }

    private void revokeToken(LoginUser user) {
       var walidateTokens = tokenRepository.findAllValidTokenByUserId(user.getId());
       if(walidateTokens.isEmpty())
           return;

       walidateTokens.forEach(item-> {
                   item.setRevoked(true);
                   item.setExpired(true);
               }
       );
       tokenRepository.saveAll(walidateTokens);
    }

    public boolean isValidToken(String token) {
        return tokenRepository.findByToken(token).isPresent();
    }

    public String getTokenById(Long id) {
          return tokenRepository.findByUserId(id).orElseThrow(()->new RuntimeException("Token Not Found"));
    }

    public void deleteUser(Long id) {
        LoginUser user = loginUserRepository.findById(id).orElseThrow(()->new RuntimeException("Business User Not Found"));
        if(user.getUserType().equals(UserType.USER)){
            loginUserRepository.deleteById(id);
        }
        loginUserRepository.deleteById(id);
        tokenRepository.deleteById(id);
    }

    public void deleteBusinessUser(Long id) {
       LoginUser businessUser = loginUserRepository.findById(id).orElseThrow(()->new RuntimeException("Business User Not Found"));
       if(businessUser.getUserType().equals(UserType.BUSINESSUSER)){
           loginUserRepository.deleteById(id);
           webClient.delete()
                   .uri("http://localhost:8082/api/v1/store/delete/"+ businessUser.getFirstName())
                   .retrieve()
                   .bodyToMono(Void.class)
                   .block();

       }
    }
}
