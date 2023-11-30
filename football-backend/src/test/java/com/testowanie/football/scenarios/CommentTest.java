package com.testowanie.football.scenarios;

import com.testowanie.football.dto.request.UpdateArticleRequest;
import com.testowanie.football.model.Article;
import com.testowanie.football.model.Category;
import com.testowanie.football.model.Comment;
import com.testowanie.football.model.Editor;
import com.testowanie.football.repository.ArticleRepository;
import com.testowanie.football.repository.CategoryRepository;
import com.testowanie.football.repository.EditorRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CommentTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String ARTICLES_ENDPOINT = "/api/v1/articles";
    private final String COMMENTS_ENDPOINT = "/comments";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private EditorRepository editorRepository;

    private Article article;
    private Comment comment;
    private Editor editor;
    private Category category;
    private Integer likes;
    private Integer dislikes;


    private ResultActions resultActions;
    private UpdateArticleRequest updateArticleRequest;

    @Before
    public void setUp() {
        cleanUp();
        category = Category.builder()
                .name("Kategoria1")
                .articles(Set.of())
                .build();
        category = categoryRepository.save(category);

        editor = Editor.builder()
                .name("Gonzalo")
                .surname("Higuain")
                .photoUrl("example")
                .build();
        editor = editorRepository.save(editor);

        article = Article.builder()
                .title("Artykul")
                .editor(editor)
                .content("tests")
                .category(category)
                .build();

        article = articleRepository.save(article);

    }

    public void cleanUp() {
        articleRepository.deleteAll();
        categoryRepository.deleteAll();
        editorRepository.deleteAll();
    }

    @Given("I have an article")
    public void iHaveAnArticle() {
        articleRepository.findById(article.getId()).orElseThrow();
    }

    // post comment
    @Given("I have a comment")
    public void iHaveAComment() {
        comment = Comment.builder()
                .nickname("nickname")
                .content("content")
                .thumbsUp(0)
                .thumbsDown(0)
                .build();
    }

    @When("I create a new comment")
    public void iCreateANewComment() throws Exception {
        var content = objectMapper.writeValueAsString(comment);
        resultActions = mockMvc.perform(post(ARTICLES_ENDPOINT + "/{id}" + COMMENTS_ENDPOINT, article.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
    }

    @Then("The comment gets added to the article")
    public void iSeeItInTheCommentSection() throws Exception {
        resultActions.andExpect(status().isCreated());
    }

    @When("I delete the comment")
    @Transactional
    public void iDeleteMyComment() throws Exception {
        article = articleRepository.findById(article.getId()).orElseThrow();
        comment = article.getComments().stream().findFirst().orElseThrow();
        resultActions = mockMvc.perform(delete(ARTICLES_ENDPOINT + "/{id}" + COMMENTS_ENDPOINT + "/{commentId}", article.getId(), comment.getId()));
    }

    @Then("The comment gets deleted from the article")
    public void theCommentGetsDeletedFromTheArticle() throws Exception {
        resultActions.andExpect(status().isNoContent());
    }

    @Given("I have an article with a comment")
    public void iHaveAnArticleWithAComment() throws Exception {
        iHaveAComment();
        iCreateANewComment();
    }

    @When("I dislike a comment")
    @Transactional
    public void iDislikeAComment() throws Exception {
        article = articleRepository.findById(article.getId()).orElseThrow();
        comment = article.getComments().stream().findFirst().orElseThrow();
        resultActions = mockMvc.perform(post(ARTICLES_ENDPOINT + "/{id}" + COMMENTS_ENDPOINT + "/{commentId}/dislike/add", article.getId(), comment.getId()));
    }

    @Then("The comment gets one more dislike")
    @Transactional
    public void theCommentGetsOneMoreDislike() throws Exception {
        article = articleRepository.findById(article.getId()).orElseThrow();
        comment = article.getComments().stream().findFirst().orElseThrow();
        assertEquals(1, comment.getThumbsDown());
        resultActions.andExpect(status().isNoContent());
    }

    @When("I like a comment")
    @Transactional
    public void iLikeAComment() throws Exception {
        article = articleRepository.findById(article.getId()).orElseThrow();
        comment = article.getComments().stream().findFirst().orElseThrow();
        resultActions = mockMvc.perform(post(ARTICLES_ENDPOINT + "/{id}" + COMMENTS_ENDPOINT + "/{commentId}/like/add", article.getId(), comment.getId()));
    }

    @Then("The comment gets one more like")
    @Transactional
    public void theCommentGetsOneMoreLike() throws Exception {
        article = articleRepository.findById(article.getId()).orElseThrow();
        comment = article.getComments().stream().findFirst().orElseThrow();
        assertEquals(1, comment.getThumbsUp());
        resultActions.andExpect(status().isNoContent());
    }

    @When("I undo leaving a like to a comment")
    @Transactional
    public void iUndoLikeAComment() throws Exception {
        article = articleRepository.findById(article.getId()).orElseThrow();
        comment = article.getComments().stream().findFirst().orElseThrow();
        likes = comment.getThumbsUp();
        resultActions = mockMvc.perform(post(ARTICLES_ENDPOINT + "/{id}" + COMMENTS_ENDPOINT + "/{commentId}/like/remove", article.getId(), comment.getId()));
    }

    @Then("The comment gets one less like")
    @Transactional
    public void theCommentGetsOneLessLike() throws Exception {
        article = articleRepository.findById(article.getId()).orElseThrow();
        comment = article.getComments().stream().findFirst().orElseThrow();
        assertEquals(likes - 1, comment.getThumbsUp());
        resultActions.andExpect(status().isNoContent());
    }

    @When("I undo leaving a dislike to a comment")
    @Transactional
    public void iUndoDislikeAComment() throws Exception {
        article = articleRepository.findById(article.getId()).orElseThrow();
        comment = article.getComments().stream().findFirst().orElseThrow();
        dislikes = comment.getThumbsDown();
        resultActions = mockMvc.perform(post(ARTICLES_ENDPOINT + "/{id}" + COMMENTS_ENDPOINT + "/{commentId}/dislike/remove", article.getId(), comment.getId()));
    }

    @Then("The comment gets one less dislike")
    @Transactional
    public void theCommentGetsOneLessDislike() throws Exception {
        article = articleRepository.findById(article.getId()).orElseThrow();
        comment = article.getComments().stream().findFirst().orElseThrow();
        assertEquals(dislikes - 1, comment.getThumbsDown());
        resultActions.andExpect(status().isNoContent());
    }
}
