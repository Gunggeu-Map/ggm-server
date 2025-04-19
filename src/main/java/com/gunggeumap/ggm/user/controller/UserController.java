package com.gunggeumap.ggm.user.controller;

import com.gunggeumap.ggm.auth.CustomUserDetails;
import com.gunggeumap.ggm.common.dto.ApiResult;
import com.gunggeumap.ggm.user.dto.response.UserMypageResponse;
import com.gunggeumap.ggm.user.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserServiceImpl userService;

//  @GetMapping
//  public ResponseEntity<ApiResult<UserMypageResponse>> getUserMypage(
//      @AuthenticationPrincipal CustomUserDetails userDetails) {
//    return ResponseEntity.ok(ApiResult.success(userService.getUserMypage(userDetails.userId())));
//  }

  // TODO : 테스트용
  @GetMapping
  public ResponseEntity<ApiResult<UserMypageResponse>> getUserMypage(
      Long userId) {
    return ResponseEntity.ok(ApiResult.success(userService.getUserMypage(userId)));
  }

}
