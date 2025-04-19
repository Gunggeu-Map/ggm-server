package com.gunggeumap.ggm.auth.oauth.strategy;

import com.gunggeumap.ggm.auth.oauth.dto.OAuth2UserInfo;

public interface OAuth2LoginStrategy {
    OAuth2UserInfo getOAuth2UserInfo(String providerAccessToken);
    String getProvider();
}
