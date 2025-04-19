package com.gunggeumap.ggm.auth.oauth.dto;

import java.time.LocalDate;

public record OAuth2UserInfo (
        String provider,
        String providerId,
        String nickname,
        LocalDate birth
){
}
