package com.testowanie.football.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
public record CreateCategoryRequest(
        @NotBlank
        @Size(min = 3, max = 200)
        String name
) {
}
