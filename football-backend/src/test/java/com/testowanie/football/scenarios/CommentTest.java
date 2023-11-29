package com.testowanie.football.scenarios;

import com.testowanie.football.model.Article;
import com.testowanie.football.model.Comment;
import com.testowanie.football.repository.ArticleRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CommentTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

    private String originalTitle = "CR7 is the GOAT";
    private String commentContent = "Siuuuu";
    private Integer likes = 2137;
    private String nickname = "Kardo93442";
    private final String ARTICLES_ENDPOINT="/articles/";
    private final String COMMENTS_ENDPOINT="/comments";



    private Long articleId;
    private Long commentId;
    private Article article;
    private Comment comment;


    private ResultActions resultActions;

    @Before
    public void setUp() {
        articleRepository.deleteAll();
        article = Article.builder()
                .title(originalTitle)
                .build();
        articleId = article.getId();
        articleRepository.save(article);

    }

    // post comment
    @Given("I have a comment for the article")
    public void iHaveACommentForTheArticle() {
        setUp();
        comment = Comment.builder()
                .content(commentContent)
                .thumbsUp(likes)
                .nickname(nickname)
                .build();
    }

    @When("I post a comment")
    public void iPostAComment() throws Exception {
        var content = objectMapper.writeValueAsString(comment);
        resultActions = mockMvc.perform(post(ARTICLES_ENDPOINT+"{id}"+COMMENTS_ENDPOINT, articleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
    }

    @Then("I see it in the comment section")
    public void iSeeItInTheCommentSection() throws Exception {
        resultActions.andExpect(status().isCreated());
    }

    // delete comment

    @And("I deleted a comment")
    public void iDeletedAComment() throws Exception {
        commentId = articleRepository.findById(articleId).get()
                .getComments().stream().findAny().get().getId();

        resultActions = mockMvc.perform(delete(ARTICLES_ENDPOINT+"{id}"+COMMENTS_ENDPOINT+"{commentId}",
                articleId, commentId));
    }

    @Then("I dont see the comment")
    public void iDontSeeTheComment() throws Exception {
        resultActions.andExpect(status().isNoContent());
        var commentExists = articleRepository.findById(articleId).get()
                .getComments().stream()
                .anyMatch(com -> Objects.equals(com.getId(), commentId));
        assertFalse(commentExists);
    }
}
