package com.gunggeumap.ggm.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoUserResponse(
        @NotNull
        Long id,
        @NotNull
        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount
) implements OAuth2UserResponse {
    public record KakaoAccount(
            @NotNull
            Profile profile,
            @JsonProperty("birthyear")
            String birthYear,
            String birthday
    ) {
        public record Profile(
                @NotBlank
                String nickname
        ) {
        }
    }

    @Override
    public OAuth2UserInfo toUserInfo(String provider) {
        LocalDate birth = LocalDate.of(
                Integer.parseInt(kakaoAccount.birthYear()),
                Integer.parseInt(kakaoAccount().birthday().substring(0, 2)),
                Integer.parseInt(kakaoAccount().birthday().substring(2))
        );

        return new OAuth2UserInfo(
                provider,
                String.valueOf(id()),
                kakaoAccount().profile().nickname(),
                birth
        );
    }
}
