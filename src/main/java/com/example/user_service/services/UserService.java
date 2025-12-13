package com.example.user_service.services;

import com.example.user_service.dtos.objects.TrackedObjectResponse;
import com.example.user_service.dtos.user.FcmTokenRequest;
import com.example.user_service.dtos.user.GeofenceRequest;
import com.example.user_service.dtos.user.PushSubscriptionRequest;
import com.example.user_service.dtos.user.UserProfileResponse;
import com.example.user_service.models.User;
import com.example.user_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserProfileResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return UserProfileResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .geofenceCenterLat(user.getGeofenceCenterLat())
                .geofenceCenterLng(user.getGeofenceCenterLng())
                .geofenceRadiusMeters(user.getGeofenceRadiusMeters())
                .trackedObjects(user.getTrackedObjects().stream()
                        .map(obj -> TrackedObjectResponse.builder()
                                .id(obj.getId())
                                .name(obj.getName())
                                .type(obj.getType())
                                .icon(obj.getIcon())
                                .latitude(obj.getLatitude())
                                .longitude(obj.getLongitude())
                                .outsideGeofence(obj.getOutsideGeofence())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public UserProfileResponse updateGeofence(Long userId, GeofenceRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setGeofenceCenterLat(request.getCenterLat());
        user.setGeofenceCenterLng(request.getCenterLng());
        user.setGeofenceRadiusMeters(request.getRadiusMeters());

        userRepository.save(user);
        return getUserProfile(userId);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Transactional
    public void savePushSubscription(Long userId, PushSubscriptionRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setPushEndpoint(request.getEndpoint());
        user.setPushP256dh(request.getKeys().getP256dh());
        user.setPushAuth(request.getKeys().getAuth());

        userRepository.save(user);
    }

    @Transactional
    public void removePushSubscription(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setPushEndpoint(null);
        user.setPushP256dh(null);
        user.setPushAuth(null);

        userRepository.save(user);
    }

    @Transactional
    public void saveFcmToken(Long userId, FcmTokenRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setFcmToken(request.getToken());
        userRepository.save(user);
    }

    @Transactional
    public void removeFcmToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setFcmToken(null);
        userRepository.save(user);
    }
}
