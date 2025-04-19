package com.gunggeumap.ggm.question;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GptAnswerParser {

  public static class ParsedResult {
    public final String content;
    public final String category;

    public ParsedResult(String content, String category) {
      this.content = content;
      this.category = category;
    }
  }

  public static ParsedResult parse(String gptJson) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode root = objectMapper.readTree(gptJson);
      String fullContent = root
          .path("choices")
          .get(0)
          .path("message")
          .path("content")
          .asText();

      // 카테고리 추출용 정규식
      Pattern pattern = Pattern.compile("카테고리[:：]\\s*(\\w+)", Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(fullContent);

      String category = null;
      if (matcher.find()) {
        category = matcher.group(1); // e.g., NATURE
      }

      // 본문에서 카테고리 줄 제거 (또는 \n기준 마지막 문장 제거)
      String[] lines = fullContent.split("\n");
      StringBuilder sb = new StringBuilder();
      for (String line : lines) {
        if (!line.startsWith("카테고리:")) {
          sb.append(line).append("\n");
        }
      }

      String content = sb.toString().trim();

      return new ParsedResult(content, category);

    } catch (Exception e) {
      throw new RuntimeException("파싱 실패: " + e.getMessage(), e);
    }
  }
}
