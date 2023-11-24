package com.testowanie.football.web;

import com.testowanie.football.model.Category;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @GetMapping
    public ResponseEntity<Pageable> getCategories(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable long id) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody Category category) {
        return ResponseEntity.created(null).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable long id, @RequestBody Category category) {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable long id) {
        return ResponseEntity.noContent().build();
    }
}
