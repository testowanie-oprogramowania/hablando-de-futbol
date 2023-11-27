package com.testowanie.football.mapper;

import com.testowanie.football.dto.request.CreateCategoryRequest;
import com.testowanie.football.dto.request.UpdateCategoryRequest;
import com.testowanie.football.dto.resource.CategoryResource;
import com.testowanie.football.model.Category;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CategoryMapper {

    public CategoryResource toCategoryResource(Category category) {
        return CategoryResource.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category fromCreateCategoryRequest(CreateCategoryRequest categoryRequest) {
        return Category.builder()
                .id(null)
                .name(categoryRequest.name())
                .articles(Set.of())
                .build();
    }

    public void updateCategoryFromUpdateCategoryRequest(UpdateCategoryRequest updateCategoryRequest, Category category) {
        category.setName(updateCategoryRequest.name());
    }
}
