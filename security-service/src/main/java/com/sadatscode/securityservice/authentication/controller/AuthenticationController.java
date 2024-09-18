package com.sadatscode.securityservice.authentication.controller;

import com.sadatscode.securityservice.authentication.AuthenticationRequest;
import com.sadatscode.securityservice.authentication.AuthenticationResponse;
import com.sadatscode.securityservice.authentication.service.AuthenticationService;
import com.sadatscode.securityservice.model.LoginUser;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import com.sadatscode.securityservice.model.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@SecurityRequirement(name="bearerForAuthenticationController")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @GetMapping("/validToken/{token}")
    public boolean isValidToken(@PathVariable String token){
        return authenticationService.isValidToken(token);
    }

    @GetMapping("/getToken/{id}")
    public ResponseEntity<String> getTokenById(@PathVariable Long id){
        return ResponseEntity.ok(authenticationService.getTokenById(id));
    }
    @GetMapping("/authenticatedUser")
    public ResponseEntity<String> authenticatedUser(@AuthenticationPrincipal LoginUser loginUser){
        return ResponseEntity.ok(loginUser.getFirstName());
    }
    @PostMapping("/registerUser")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
      return ResponseEntity.ok(authenticationService.registerUser(registerRequest));
    }
    @PostMapping("/registerBusinessUser")
    public ResponseEntity<AuthenticationResponse> registerBusinessUser(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.registerBusinessUser(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.login(authenticationRequest));
    }
    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable Long id){
         authenticationService.deleteUser(id);
    }

    @DeleteMapping("/deleteBusinessUser/{id}")
    public void deleteBusinessUser(@PathVariable Long id){
      authenticationService.deleteBusinessUser(id);
    }

}
