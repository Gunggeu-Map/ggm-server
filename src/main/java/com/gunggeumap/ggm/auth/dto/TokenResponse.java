package com.gunggeumap.ggm.auth.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
