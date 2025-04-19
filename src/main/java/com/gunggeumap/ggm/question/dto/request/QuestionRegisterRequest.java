package com.gunggeumap.ggm.question.dto.request;

import com.gunggeumap.ggm.question.entity.Question;
import com.gunggeumap.ggm.question.enums.Category;
import com.gunggeumap.ggm.user.entity.User;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.locationtech.jts.geom.Point;

public record QuestionRegisterRequest(
    @NotNull
    Long userId,

    @NotBlank
    String title,

    @NotBlank
    String content,

    @Nullable
    String imageUrl,

    @NotNull
    Float latitude,

    @NotNull
    Float longitude,

    @NotNull
    Boolean isPublic
) {
  public Question toEntity(User user, Point point) {
    return new Question(
        user,
        this.title,
        this.content,
        this.imageUrl,
        point,
        this.isPublic
    );
  }
}

