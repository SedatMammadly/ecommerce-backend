package com.sadatscode.securityservice.authentication.controller;

import com.sadatscode.securityservice.authentication.ChangePasswordRequest;
import com.sadatscode.securityservice.authentication.service.LoginUserService;
import com.sadatscode.securityservice.model.LoginUser;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuthForPassword")
public class PasswordController {
 private final LoginUserService service;
    @PatchMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, @AuthenticationPrincipal UserDetails userDetails){
      service.changePassword(request,userDetails);
      return ResponseEntity.ok().build();
    }
}
