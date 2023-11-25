package com.testowanie.football.dto.resource;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record EditorResource(
        Long id,
        String name,
        String surname,
        String image
) {
}
