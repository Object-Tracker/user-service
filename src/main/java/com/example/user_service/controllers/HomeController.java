package com.example.user_service.controllers;

import com.example.user_service.dtos.home.HomeUserDetailsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
    @GetMapping
    public ResponseEntity<HomeUserDetailsResponse> getUserDetails(
            @AuthenticationPrincipal UserDetails userDetails
            ) {
        String username = userDetails.getUsername();
        return ResponseEntity.ok(new HomeUserDetailsResponse(username));
    }
}
