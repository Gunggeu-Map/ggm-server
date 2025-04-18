package com.gunggeumap.ggm.shortinfo.dto.response;

import com.gunggeumap.ggm.shortinfo.entity.ShortInfo;

public record ShortInfoResponse(
    Long id,
    String content
) {

  public static ShortInfoResponse from(ShortInfo shortInfo) {
    return new ShortInfoResponse(shortInfo.getId(), shortInfo.getContent());
  }

}
