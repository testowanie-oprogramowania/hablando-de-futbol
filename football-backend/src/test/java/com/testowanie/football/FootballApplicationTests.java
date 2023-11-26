package com.testowanie.football;

import com.testowanie.football.model.Article;
import com.testowanie.football.model.Category;
import com.testowanie.football.model.Comment;
import com.testowanie.football.model.Editor;
import com.testowanie.football.repository.ArticleRepository;
import com.testowanie.football.repository.CategoryRepository;
import com.testowanie.football.repository.EditorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class FootballApplicationTests {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EditorRepository editorRepository;

    @Test
    void auditingTest() {
        // given
        Editor savedEditor = editorRepository.save(Editor.builder().name("name").surname("surname").build());
        Category savedCategory = categoryRepository.save(Category.builder().name("name").articles(new HashSet<>()).build());
        Article article = Article.builder()
                .title("title")
                .content("content")
                .photoUrl("photoUrl")
                .editor(savedEditor)
                .category(savedCategory)
                .comments(new HashSet<>())
                .build();

        // when
        Article savedArticle = articleRepository.save(article);
        savedArticle.getComments().add(Comment.builder().nickname("jasiu").content("content").thumbsDown(0).thumbsUp(2).build());
        Article updatedArticle = articleRepository.save(savedArticle);

        //then
        assertThat(updatedArticle.getCreatedDate()).isNotEqualTo(updatedArticle.getLastModifiedDate());
    }

}
