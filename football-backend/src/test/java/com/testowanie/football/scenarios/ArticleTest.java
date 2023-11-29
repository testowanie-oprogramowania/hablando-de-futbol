package com.testowanie.football.scenarios;

import com.testowanie.football.dto.request.UpdateArticleRequest;
import com.testowanie.football.model.Article;
import com.testowanie.football.model.Category;
import com.testowanie.football.model.Editor;
import com.testowanie.football.repository.ArticleRepository;
import com.testowanie.football.repository.CategoryRepository;
import com.testowanie.football.repository.EditorRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@CucumberContextConfiguration
@ActiveProfiles("local")
public class ArticleTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private EditorRepository editorRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    String originalTitle = "Glik is the best";
    String articleContent = "Glik is the GOAT (Greatest of all time)";
    String newTitle = "The man the myth the legend";



    private Article article;
    private Category category;
    private Editor editor;
    private Long exampleId=1L;
    private ResultActions resultActions;
    private UpdateArticleRequest updateArticleRequest;

    private final String ARTICLES_ENDPOINT="/api/v1/articles";

    @Before
    public void setUp() {
        editorRepository.deleteAll();
        editor = editorRepository.save(new Editor(exampleId,"Zbyszek","JSON", null));
        categoryRepository.deleteAll();
        category = categoryRepository.save(new Category(exampleId, "La Liga", new HashSet<Article>()));
        articleRepository.deleteAll();
    }

    // Feature: post article
    @Given("I have article text prepared")
    public void iHaveAnArticleTextPrepared() {
        article = Article.builder()
                .title(originalTitle)
                .content(articleContent)
                .editor(editor)
                .content("hwdp")
                .category(category)
                .build();
        articleRepository.save(article);
    }

    @When("I create an article")
    public void iCreateAnArticle() throws Exception {
        var articleAttributes = objectMapper.writeValueAsString(article);

        resultActions = mockMvc.perform(post(ARTICLES_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(articleAttributes));
    }

    @When("I list all articles")
    public void iListAllArticles() throws Exception {
        resultActions = mockMvc.perform(get(ARTICLES_ENDPOINT+"/1"));
    }

    @Then("I see new article")
    public void iSeeNewArticle() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(article.getTitle()))
                .andExpect(jsonPath("$.content").value(article.getContent()));
    }

    // get existing article

    @When("I request an article by its id")
    public void iRequestAnArticleByItsId() throws Exception {
        resultActions = mockMvc.perform(get(ARTICLES_ENDPOINT+"/{id}",article.getId()))
                .andExpect(status().isOk());
    }

    @Then("I get details of requested article")
    public void iGetDetailsOfRequestedArticle() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").exists(),
                        jsonPath("$.title").value(article.getTitle()),
                        jsonPath("$.content").value(article.getContent()));
    }

    // Update existing article

    @And("I have an update for the article")
    public void iHaveAnUpdateForTheArticle() {
         updateArticleRequest = UpdateArticleRequest
                 .builder()
                 .title(newTitle)
                 .content(articleContent)
                 .editorId(editor.getId())
                 .categoryName(category.getName())
                 .build();
    }

    @When("I update the article")
    public void iUpdateTheArticle() throws Exception {
        var updatedInfo = objectMapper.writeValueAsString(updateArticleRequest);
        resultActions = mockMvc.perform(patch(ARTICLES_ENDPOINT+"/{id}", article.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedInfo));
        article = articleRepository.findById(article.getId()).orElse(null);
    }

    @Then("I see updated article")
    public void iSeeUpdatedArticle() throws Exception {
        resultActions.andExpect(status().isNoContent());
        assertEquals(article.getTitle(), newTitle);
    }

    // delete existing article

    @When("I delete the article")
    public void iDeleteTheArticle() throws Exception {
        resultActions = mockMvc.perform(delete(ARTICLES_ENDPOINT+"/{id}", article.getId()))
                .andExpect(status().isNoContent());
    }

    @Then("I dont see the article anymore")
    public void iDontSeeTheArticleAnymore() throws Exception {
        mockMvc.perform(get(ARTICLES_ENDPOINT+"/{id}", article.getId()))
                .andExpect(status().isNotFound());
    }
}
