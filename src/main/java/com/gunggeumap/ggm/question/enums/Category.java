package com.gunggeumap.ggm.question.enums;

import lombok.Getter;

@Getter
public enum Category {
  NATURE("자연"),
  SPACE("우주"),
  TECHNOLOGY("기술"),
  HUMAN_BODY("인체"),
  ENVIRONMENT("환경"),
  PHYSICS("물리"),
  CHEMISTRY("화학"),
  BIOLOGY("생물"),
  EARTH("지구과학"),
  DAILY("일상 속 궁금증"),
  ETC("기타");

  private final String label;

  Category(String label) {
    this.label = label;
  }
}
