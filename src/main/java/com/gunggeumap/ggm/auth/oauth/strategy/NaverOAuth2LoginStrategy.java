package com.gunggeumap.ggm.auth.oauth.strategy;

import com.gunggeumap.ggm.auth.oauth.dto.NaverUserResponse;
import com.gunggeumap.ggm.auth.oauth.dto.OAuth2UserInfo;
import com.gunggeumap.ggm.auth.util.OAuth2ClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NaverOAuth2LoginStrategy implements OAuth2LoginStrategy {

    private final OAuth2ClientUtil oAuth2ClientUtil;
    private final String PROVIDER_URL;
    private final String PROVIDER;

    public NaverOAuth2LoginStrategy(OAuth2ClientUtil oAuth2ClientUtil,
                                    @Value("${spring.oauth2.client.provider.naver.name}") String provider_name,
                                    @Value("${spring.oauth2.client.provider.naver.url}") String provider_url
    ) {
        this.oAuth2ClientUtil = oAuth2ClientUtil;
        this.PROVIDER = provider_name;
        this.PROVIDER_URL = provider_url;
    }

    @Override
    public OAuth2UserInfo getOAuth2UserInfo(String providerAccessToken) {
        NaverUserResponse naverUserResponse = oAuth2ClientUtil.getUserInfo(
                PROVIDER_URL,
                providerAccessToken,
                NaverUserResponse.class);

        return (naverUserResponse == null) ? null
                : naverUserResponse.toUserInfo(PROVIDER);
    }

    @Override
    public String getProvider() {
        return PROVIDER;
    }
}
