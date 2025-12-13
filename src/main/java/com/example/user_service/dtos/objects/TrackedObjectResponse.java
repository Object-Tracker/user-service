package com.example.user_service.dtos.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackedObjectResponse {
    private Long id;
    private String name;
    private String type;
    private String icon;
    private Double latitude;
    private Double longitude;
    private Boolean outsideGeofence;
}
