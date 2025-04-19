package com.gunggeumap.ggm.auth.oauth.strategy;

import com.gunggeumap.ggm.auth.oauth.dto.KakaoUserResponse;
import com.gunggeumap.ggm.auth.oauth.dto.OAuth2UserInfo;
import com.gunggeumap.ggm.auth.util.OAuth2ClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KakaoOAuth2LoginStrategy implements OAuth2LoginStrategy {

    private final OAuth2ClientUtil oAuth2ClientUtil;
    private final String PROVIDER_URL;
    private final String PROVIDER;

    public KakaoOAuth2LoginStrategy(OAuth2ClientUtil oAuth2ClientUtil,
                                    @Value("${spring.oauth2.client.provider.kakao.name}") String provider_name,
                                    @Value("${spring.oauth2.client.provider.kakao.url}") String provider_url
    ) {
        this.oAuth2ClientUtil = oAuth2ClientUtil;
        this.PROVIDER = provider_name;
        this.PROVIDER_URL = provider_url;
    }

    @Override
    public OAuth2UserInfo getOAuth2UserInfo(String providerAccessToken) {
        KakaoUserResponse kakaoUserResponse = oAuth2ClientUtil.getUserInfo(
                PROVIDER_URL,
                providerAccessToken,
                KakaoUserResponse.class);

        return (kakaoUserResponse == null) ? null
                : kakaoUserResponse.toUserInfo(PROVIDER);
    }

    @Override
    public String getProvider() {
        return PROVIDER;
    }
}
