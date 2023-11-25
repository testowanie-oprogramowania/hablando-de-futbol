package com.testowanie.football.service.internal;

import com.testowanie.football.dto.request.CreateCategoryRequest;
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
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryUseCases {
    private final String CATEGORY_NOT_FOUND = "Category not found";
    private final String CATEGORY_ALREADY_EXISTS = "Category with the given name already exists";
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
            Category category = new Category(null, categoryRequest.name(), Set.of());
            categoryRepository.save(category);
        } catch (Exception e) {
            throw new IllegalArgumentException(CATEGORY_ALREADY_EXISTS);
        }
    }

    @Override
    @Transactional
    public void updateCategory(Long id, Category category) {
        throw new IllegalArgumentException();
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        final Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.delete(category.get());
        } else {
            throw new EntityNotFoundException(CATEGORY_NOT_FOUND);
        }
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
