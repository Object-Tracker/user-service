package com.example.user_service.controllers;

import com.example.user_service.dtos.user.FcmTokenRequest;
import com.example.user_service.dtos.user.GeofenceRequest;
import com.example.user_service.dtos.user.PushSubscriptionRequest;
import com.example.user_service.dtos.user.UserProfileResponse;
import com.example.user_service.models.User;
import com.example.user_service.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getCurrentUser(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(userService.getUserProfile(user.getUserId()));
    }

    @PutMapping("/geofence")
    public ResponseEntity<UserProfileResponse> updateGeofence(
            @Valid @RequestBody GeofenceRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(userService.updateGeofence(user.getUserId(), request));
    }

    @PostMapping("/push/subscribe")
    public ResponseEntity<Void> subscribePush(
            @RequestBody PushSubscriptionRequest request,
            @AuthenticationPrincipal User user
    ) {
        userService.savePushSubscription(user.getUserId(), request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/push/subscribe")
    public ResponseEntity<Void> unsubscribePush(
            @AuthenticationPrincipal User user
    ) {
        userService.removePushSubscription(user.getUserId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/fcm/token")
    public ResponseEntity<Void> saveFcmToken(
            @RequestBody FcmTokenRequest request,
            @AuthenticationPrincipal User user
    ) {
        userService.saveFcmToken(user.getUserId(), request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/fcm/token")
    public ResponseEntity<Void> removeFcmToken(
            @AuthenticationPrincipal User user
    ) {
        userService.removeFcmToken(user.getUserId());
        return ResponseEntity.ok().build();
    }
}
