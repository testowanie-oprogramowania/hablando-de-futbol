package com.testowanie.football.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.extern.jackson.Jacksonized;
import lombok.Builder;

@Builder
@Jacksonized
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record UpdateArticleRequest(
        @NotBlank @Size(min = 3, max = 255) String title,
        @NotNull @Positive Long editorId,
        @NotBlank @Size(max = 10000) String content,
        @NotBlank @Size(max = 255) String photoUrl,
        @NotBlank @Size(min = 3, max = 200) String categoryName
) {
}
