package com.example.user_service.controllers;

import com.example.user_service.dtos.objects.TrackedObjectRequest;
import com.example.user_service.dtos.objects.TrackedObjectResponse;
import com.example.user_service.models.User;
import com.example.user_service.services.TrackedObjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/objects")
@RequiredArgsConstructor
public class TrackedObjectController {

    private final TrackedObjectService trackedObjectService;

    @GetMapping
    public ResponseEntity<List<TrackedObjectResponse>> getAllObjects(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(trackedObjectService.getObjectsByUser(user.getUserId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackedObjectResponse> getObject(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(trackedObjectService.getObjectById(id, user.getUserId()));
    }

    @PostMapping
    public ResponseEntity<TrackedObjectResponse> createObject(
            @Valid @RequestBody TrackedObjectRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(trackedObjectService.createObject(request, user.getUserId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrackedObjectResponse> updateObject(
            @PathVariable Long id,
            @Valid @RequestBody TrackedObjectRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(trackedObjectService.updateObject(id, request, user.getUserId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObject(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        trackedObjectService.deleteObject(id, user.getUserId());
        return ResponseEntity.noContent().build();
    }
}
