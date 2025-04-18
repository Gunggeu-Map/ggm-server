package com.gunggeumap.ggm.shortinfo.service;

import com.gunggeumap.ggm.shortinfo.dto.response.ShortInfoResponse;
import java.util.List;

public interface ShortInfoService {
  
  List<ShortInfoResponse> getAllShortInfo();
}
