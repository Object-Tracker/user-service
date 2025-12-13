package com.example.user_service.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushSubscriptionRequest {
    private String endpoint;
    private Keys keys;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Keys {
        private String p256dh;
        private String auth;
    }
}
