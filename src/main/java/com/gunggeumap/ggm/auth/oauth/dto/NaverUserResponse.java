package com.gunggeumap.ggm.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.StringTokenizer;

public record NaverUserResponse(
        @NotNull
        Response response
) implements OAuth2UserResponse {
    public record Response(
            @NotBlank
            String id,
            @NotBlank
            String nickname,
            @NotBlank
            @JsonProperty("birthyear")
            String birthYear,
            @JsonProperty("birthday")
            String birthday
    ) {
    }

    @Override
    public OAuth2UserInfo toUserInfo(String provider) {
        StringTokenizer str = new StringTokenizer(response().birthday(), "-");

        LocalDate birth = LocalDate.of(
                Integer.parseInt(response().birthYear()),
                Integer.parseInt(str.nextToken()),
                Integer.parseInt(str.nextToken())
        );

        return new OAuth2UserInfo(
                provider,
                response().id(),
                response().nickname(),
                birth
        );
    }
}
