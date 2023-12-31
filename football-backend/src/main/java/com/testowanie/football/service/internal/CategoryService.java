package com.testowanie.football.service.internal;

import com.testowanie.football.dto.request.CreateCategoryRequest;
import com.testowanie.football.dto.request.UpdateCategoryRequest;
import com.testowanie.football.dto.resource.CategoryArticlesResource;
import com.testowanie.football.dto.resource.CategoryResource;
import com.testowanie.football.exception.EntityNotFoundException;
import com.testowanie.football.mapper.ArticleMapper;
import com.testowanie.football.mapper.CategoryMapper;
import com.testowanie.football.model.Category;
import com.testowanie.football.repository.CategoryRepository;
import com.testowanie.football.service.CategoryUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class CategoryService implements CategoryUseCases {
    private static final String CATEGORY_NOT_FOUND = "Category not found";
    private static final String CATEGORY_ALREADY_EXISTS = "Category with the given name already exists";
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ArticleMapper articleMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResource> findAll() {
        return categoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::toCategoryResource)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryArticlesResource findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(this::buildCategoryArticlesResource)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND));
    }

    @Override
    @Transactional
    public void createCategory(CreateCategoryRequest categoryRequest) {
        try {
            Category category = categoryMapper.fromCreateCategoryRequest(categoryRequest);
            categoryRepository.save(category);
        } catch (Exception e) {
            throw new IllegalArgumentException(CATEGORY_ALREADY_EXISTS);
        }
    }

    @Override
    @Transactional
    public void updateCategory(Long id, UpdateCategoryRequest categoryRequest) {
        Category categoryToUpdate = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND));

        if (categoryToUpdate.getName().equals(categoryRequest.name())) {
            return;
        }

        if (categoryRepository.existsByName(categoryRequest.name())) {
            throw new IllegalArgumentException(CATEGORY_ALREADY_EXISTS);
        }

        categoryMapper.updateCategoryFromUpdateCategoryRequest(categoryRequest, categoryToUpdate);
        categoryRepository.save(categoryToUpdate);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND));
        categoryRepository.delete(category);
    }

    private CategoryArticlesResource buildCategoryArticlesResource(Category category) {
        return CategoryArticlesResource.builder()
                .category(categoryMapper.toCategoryResource(category))
                .articles(
                        category.getArticles()
                                .stream()
                                .map(articleMapper::toArticleResource)
                                .toList()
                )
                .build();
    }
}
