package com.testowanie.football.repository;

import com.testowanie.football.model.Article;
import com.testowanie.football.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Article> findAllByCategory(Category category, Pageable pageable);
}
