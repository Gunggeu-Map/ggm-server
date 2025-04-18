package com.gunggeumap.ggm.answer.enums;

import lombok.Getter;

@Getter
public enum VoteType {
  UP("좋아요"),
  DOWN("싫어요");

  private final String label;

  VoteType(String label) {
    this.label = label;
  }
}
