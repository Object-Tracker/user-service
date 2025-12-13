package com.example.user_service.services;

import com.example.user_service.dtos.objects.TrackedObjectRequest;
import com.example.user_service.dtos.objects.TrackedObjectResponse;
import com.example.user_service.models.TrackedObject;
import com.example.user_service.models.User;
import com.example.user_service.repositories.TrackedObjectRepository;
import com.example.user_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrackedObjectService {

    private final TrackedObjectRepository trackedObjectRepository;
    private final UserRepository userRepository;

    public List<TrackedObjectResponse> getObjectsByUser(Long userId) {
        return trackedObjectRepository.findByUserUserId(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TrackedObjectResponse getObjectById(Long objectId, Long userId) {
        TrackedObject obj = trackedObjectRepository.findByIdAndUserUserId(objectId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Object not found"));
        return toResponse(obj);
    }

    @Transactional
    public TrackedObjectResponse createObject(TrackedObjectRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        TrackedObject obj = TrackedObject.builder()
                .name(request.getName())
                .type(request.getType())
                .icon(request.getIcon())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .outsideGeofence(false)
                .user(user)
                .build();

        TrackedObject saved = trackedObjectRepository.save(obj);
        return toResponse(saved);
    }

    @Transactional
    public TrackedObjectResponse updateObject(Long objectId, TrackedObjectRequest request, Long userId) {
        TrackedObject obj = trackedObjectRepository.findByIdAndUserUserId(objectId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Object not found"));

        obj.setName(request.getName());
        obj.setType(request.getType());
        obj.setIcon(request.getIcon());
        if (request.getLatitude() != null) {
            obj.setLatitude(request.getLatitude());
        }
        if (request.getLongitude() != null) {
            obj.setLongitude(request.getLongitude());
        }

        TrackedObject saved = trackedObjectRepository.save(obj);
        return toResponse(saved);
    }

    @Transactional
    public void deleteObject(Long objectId, Long userId) {
        TrackedObject obj = trackedObjectRepository.findByIdAndUserUserId(objectId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Object not found"));
        trackedObjectRepository.delete(obj);
    }

    private TrackedObjectResponse toResponse(TrackedObject obj) {
        return TrackedObjectResponse.builder()
                .id(obj.getId())
                .name(obj.getName())
                .type(obj.getType())
                .icon(obj.getIcon())
                .latitude(obj.getLatitude())
                .longitude(obj.getLongitude())
                .outsideGeofence(obj.getOutsideGeofence())
                .build();
    }
}
