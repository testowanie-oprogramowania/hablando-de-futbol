package com.testowanie.football.web;

import com.testowanie.football.dto.request.CreateCategoryRequest;
import com.testowanie.football.dto.resource.CategoryArticlesResource;
import com.testowanie.football.dto.resource.CategoryResource;
import com.testowanie.football.model.Category;
import com.testowanie.football.service.CategoryUseCases;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryUseCases categoryUseCases;

    @GetMapping
    public ResponseEntity<List<CategoryResource>> getCategories() {
        final List<CategoryResource> categories = categoryUseCases.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryArticlesResource> getCategoryById(@PathVariable long id) {
        final CategoryArticlesResource category = categoryUseCases.findCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody @Valid CreateCategoryRequest categoryRequest) {
        categoryUseCases.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable long id, @RequestBody Category category) {
        categoryUseCases.updateCategory(id, category);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable long id) {
        categoryUseCases.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
