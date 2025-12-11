package com.example.user_service.controllers.auth;

import com.example.user_service.dtos.auth.AuthenticationResponse;
import com.example.user_service.dtos.auth.LoginRequest;
import com.example.user_service.dtos.auth.RegisterRequest;
import com.example.user_service.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) throws IOException {
        AuthenticationResponse response = this.authenticationService.register(request);
        ResponseCookie cookie = ResponseCookie.from("token", response.getToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(24 * 60 * 60 * 3) // 3 days
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("message", "Register successful"));
//        return ResponseEntity.ok(this.authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request
    ) {
        AuthenticationResponse response = this.authenticationService.login(request);
        ResponseCookie cookie = ResponseCookie.from("token", response.getToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/")
                .maxAge(24 * 60 * 60 * 3) // 3 days
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("message", "Login successful"));
//        return ResponseEntity.ok(this.authenticationService.login(request));
    }
}
