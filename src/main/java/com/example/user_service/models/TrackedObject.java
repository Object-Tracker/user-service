package com.example.user_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tracked_objects")
public class TrackedObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type; // KEYS, BICYCLE, BAG, LAPTOP, PHONE, OTHER

    private String icon; // optional icon identifier

    // Current location
    private Double latitude;
    private Double longitude;

    // Is object currently outside geofence
    @Builder.Default
    private Boolean outsideGeofence = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
}
