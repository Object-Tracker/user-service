package com.example.user_service.services;

import com.example.user_service.config.auth.JwtService;
import com.example.user_service.dtos.auth.AuthenticationResponse;
import com.example.user_service.dtos.auth.LoginRequest;
import com.example.user_service.dtos.auth.RegisterRequest;
import com.example.user_service.models.User;
import com.example.user_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest request) throws IOException {
        boolean exists = this.userRepository.findByUsername(request.getUsername()).isPresent();
        if (exists) {
            throw new IllegalArgumentException("Email already registered");
        }

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .userPassword(this.passwordEncoder.encode(request.getUserPassword()))
                .build();

        this.userRepository.save(user);

        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) {
        var user = this.userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
