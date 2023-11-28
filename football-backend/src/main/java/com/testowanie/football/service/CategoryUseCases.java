package com.testowanie.football.service;

import com.testowanie.football.dto.request.CreateCategoryRequest;
import com.testowanie.football.dto.request.UpdateCategoryRequest;
import com.testowanie.football.dto.resource.CategoryArticlesResource;
import com.testowanie.football.dto.resource.CategoryResource;

import java.util.List;

public interface CategoryUseCases {

    List<CategoryResource> findAll();

    CategoryArticlesResource findCategoryById(Long id);

    void createCategory(CreateCategoryRequest categoryRequest);

    void updateCategory(Long id, UpdateCategoryRequest categoryRequest);

    void deleteCategory(Long id);
}
