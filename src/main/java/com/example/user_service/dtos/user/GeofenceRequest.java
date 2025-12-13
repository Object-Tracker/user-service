package com.example.user_service.dtos.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeofenceRequest {
    @NotNull(message = "Center latitude is required")
    private Double centerLat;

    @NotNull(message = "Center longitude is required")
    private Double centerLng;

    @NotNull(message = "Radius is required")
    @Positive(message = "Radius must be positive")
    private Double radiusMeters;
}
