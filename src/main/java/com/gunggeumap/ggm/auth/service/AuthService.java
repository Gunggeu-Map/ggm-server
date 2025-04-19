package com.gunggeumap.ggm.auth.service;

import com.gunggeumap.ggm.auth.dto.TokenResponse;
import com.gunggeumap.ggm.auth.exception.InvalidTokenException;
import com.gunggeumap.ggm.auth.oauth.dto.OAuth2UserInfo;
import com.gunggeumap.ggm.auth.oauth.strategy.OAuth2StrategyContext;
import com.gunggeumap.ggm.auth.util.JwtUtil;
import com.gunggeumap.ggm.user.entity.User;
import com.gunggeumap.ggm.user.exception.UserNotFoundException;
import com.gunggeumap.ggm.user.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final OAuth2StrategyContext oAuth2StrategyContext;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final long ACCESS_TOKEN_VALIDITY_TIME = 60 * 60 * 1000L; // 1시간
    private final long REFRESH_TOKEN_VALIDITY_TIME = 14 * 24 * 60 * 60 * 1000L; // 2주

    public TokenResponse login(String provider, String providerAccessToken) {
        OAuth2UserInfo oAuth2UserInfo = oAuth2StrategyContext.getStrategy(provider)
                .getOAuth2UserInfo(providerAccessToken);
        if (oAuth2UserInfo == null) {
            throw new UserNotFoundException(String.format("%s 소셜 유저 정보 조회에 실패했습니다.", provider));
        }

        User user = getOrCreateUser(oAuth2UserInfo);
        return issueToken(user.getId());
    }

    // refreshToken 유효성 검증 및 추출
    public TokenResponse reissueToken(String refreshToken) {
        User user;
        try {
            user = getUserFromRefreshToken(refreshToken);
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            log.debug("refresh Token 만료");
            throw new InvalidTokenException("refresh Token이 만료되었습니다.");
        } catch (JwtException | IllegalArgumentException e) {
            // 토큰 형식이 이상한 경우
            throw new InvalidTokenException("토큰 형식이 잘못되었습니다.");
        } catch (UserNotFoundException e) {
            // User 조회 실패 : 존재 하지 않거나 탈퇴한 회원임
            log.debug("User 조회 실패, 존재 하지 않는 회원임");
            throw e;
        }

        if (!refreshTokenService.validateRefreshToken(user.getId(), refreshToken)) {
            throw new InvalidTokenException("저장된 RefreshToken 과 일치하지 않습니다.");
        }
        return issueToken(user.getId());
    }

    public TokenResponse issueToken(Long userId) {
        String accessToken = jwtUtil.generateAccessToken(String.valueOf(userId), ACCESS_TOKEN_VALIDITY_TIME);
        String refreshToken = jwtUtil.generateRefreshToken(String.valueOf(userId), REFRESH_TOKEN_VALIDITY_TIME);
        refreshTokenService.saveRefreshToken(userId, refreshToken, REFRESH_TOKEN_VALIDITY_TIME);
        return new TokenResponse(accessToken, refreshToken);
    }

    public User getUserFromAccessToken(String token) {
        Long userId = jwtUtil.getUserIDFromAccessToken(token);
        if (userId == null) {
            throw new IllegalArgumentException();
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재 하지 않는 회원입니다."));
    }

    private User getUserFromRefreshToken(String token) {
        Long userId = jwtUtil.getUserIDFromRefreshToken(token);
        if (userId == null) {
            throw new IllegalArgumentException();
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("존재 하지 않는 회원입니다."));
    }


    private User getOrCreateUser(OAuth2UserInfo oAuth2UserInfo) {
        System.out.println(oAuth2UserInfo.provider()+" "+oAuth2UserInfo.providerId());
        User user = userRepository.findByProviderAndProviderId(oAuth2UserInfo.provider(), oAuth2UserInfo.providerId())
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .provider(oAuth2UserInfo.provider())
                                .providerId(oAuth2UserInfo.providerId())
                                .nickname(oAuth2UserInfo.nickname())
                                .birth(oAuth2UserInfo.birth())
                                .build())
                );
        return user;
    }
}
