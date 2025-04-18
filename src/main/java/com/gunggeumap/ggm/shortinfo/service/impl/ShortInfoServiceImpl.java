package com.gunggeumap.ggm.shortinfo.service.impl;

import com.gunggeumap.ggm.shortinfo.dto.response.ShortInfoResponse;
import com.gunggeumap.ggm.shortinfo.repository.ShortInfoRepository;
import com.gunggeumap.ggm.shortinfo.service.ShortInfoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortInfoServiceImpl implements ShortInfoService {

  private final ShortInfoRepository shortInfoRepository;

  @Override
  public List<ShortInfoResponse> getAllShortInfo() {
    return shortInfoRepository.findAllBy().stream()
        .map(ShortInfoResponse::from)
        .toList();
  }
}
