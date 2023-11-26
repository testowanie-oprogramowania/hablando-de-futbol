package com.testowanie.football.mapper;

import com.testowanie.football.dto.resource.CategoryResource;
import com.testowanie.football.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResource toCategoryResource(Category category) {
        return CategoryResource.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
