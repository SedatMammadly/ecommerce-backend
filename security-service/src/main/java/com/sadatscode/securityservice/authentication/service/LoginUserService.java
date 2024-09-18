package com.sadatscode.securityservice.authentication.service;

import com.sadatscode.securityservice.authentication.ChangePasswordRequest;
import com.sadatscode.securityservice.model.LoginUser;
import com.sadatscode.securityservice.repository.LoginUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class LoginUserService {
    private final LoginUserRepository loginUserRepository;
    private final PasswordEncoder passwordEncoder;
    public void changePassword(ChangePasswordRequest request, UserDetails authenticationUser) {
       LoginUser user= (LoginUser) authenticationUser;

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw  new IllegalStateException("Password are not the same");
        }
        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw  new IllegalStateException("Password does not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        loginUserRepository.save(user);

    }
}
