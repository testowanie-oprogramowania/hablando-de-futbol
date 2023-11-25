package com.testowanie.football.dto.resource;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record CategoryResource(
        Long id,
        String name
) {
}
