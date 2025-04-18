package com.gunggeumap.ggm.shortinfo.controller;


import com.gunggeumap.ggm.common.dto.ApiResult;
import com.gunggeumap.ggm.shortinfo.dto.response.ShortInfoResponse;
import com.gunggeumap.ggm.shortinfo.service.ShortInfoService;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/short-infos")
public class ShortInfoController {

  private final ShortInfoService shortInfoService;

  @GetMapping
  public ResponseEntity<ApiResult<List<ShortInfoResponse>>> getShortInfos() {
    return ResponseEntity.ok(ApiResult.success(shortInfoService.getAllShortInfo()));
  }

}
