package com.gunggeumap.ggm.user.service;

import com.gunggeumap.ggm.user.dto.response.UserMypageResponse;

public interface UserService {
  UserMypageResponse getUserMypage(Long userId);
}
