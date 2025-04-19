package com.gunggeumap.ggm.auth.oauth.dto;

public interface OAuth2UserResponse {
    OAuth2UserInfo toUserInfo(String provider);
}
