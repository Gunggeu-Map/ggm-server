package com.gunggeumap.ggm.auth.filter;

import com.gunggeumap.ggm.auth.CustomUserDetails;
import com.gunggeumap.ggm.auth.service.AuthService;
import com.gunggeumap.ggm.auth.util.HeaderUtil;
import com.gunggeumap.ggm.user.entity.User;
import com.gunggeumap.ggm.user.exception.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HeaderUtil headerUtil;
    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // request에서 token 추출
        String token = headerUtil.resolveToken(request);

        // 토큰 없는 요청인 경우 필터 넘어감
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        User user;
        try {
            // userID 추출
            user = authService.getUserFromAccessToken(token);
            // SecurityContextHolder에 Authentication 저장
            CustomUserDetails userDetails = new CustomUserDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (UserNotFoundException e) {
            // User 조회 실패 : 존재 하지 않거나 탈퇴한 회원임
            log.debug("존재하지 않는 User임");
            response.setStatus(HttpServletResponse.SC_GONE);
            return;
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (JwtException | IllegalArgumentException e) {
            // 토큰 형식이 이상한 경우
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        } catch (Exception e) {
            log.error("알 수 없는 오류가 발생하였습니다. 다시 시도해주세요.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return false;
    }
}
