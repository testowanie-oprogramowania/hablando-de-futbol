package com.testowanie.football.scenarios;

import com.testowanie.football.dto.request.UpdateCategoryRequest;
import com.testowanie.football.model.Article;
import com.testowanie.football.model.Category;
import com.testowanie.football.model.Editor;
import com.testowanie.football.repository.ArticleRepository;
import com.testowanie.football.repository.CategoryRepository;
import com.testowanie.football.repository.EditorRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class CategoryTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private EditorRepository editorRepository;

    private String categoryName = "La Liga";
    private Long exampleId=1L;
    private String getCategoryName2 = "Champions League";
    private final String CATEGORY_ENDPOINT="/api/v1/categories";

    private Category category;
    private Editor editor;
    private UpdateCategoryRequest updateCategoryRequest;
    private ResultActions resultActions;

    @Before
    public void setUp() {
        //editorRepository.deleteAll();
        //editor = editorRepository.save(new Editor(exampleId,"Zbyszek","JSON", null));
        categoryRepository.deleteAll();
        //articleRepository.deleteAll();
    }

    // post category

    @Given("I have a category")
    public void iHaveACategory() {
        category = Category.builder()
                .name(categoryName)
                .articles(new HashSet<>())
                .build();
        categoryRepository.save(category);
    }

    @When("I create new category")
    public void iCreateNewCategory() throws Exception {
        var content = objectMapper.writeValueAsString(category);
        resultActions = mockMvc.perform(post(CATEGORY_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
    }

    @When("I list all categories")
    public void iListAllCategories() throws Exception {
        resultActions = mockMvc.perform(get(CATEGORY_ENDPOINT+"/1"));
    }

    @Then("I see new category")
    public void iSeeNewCategory() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(category.getName()));
    }

    // Get existing category

    @Given("There is existing category")
    public void thereIsExistingCategory() {
        category = Category.builder()
                .name(categoryName)
                .build();
        categoryRepository.save(category);
    }

    @When("I request category by its id")
    public void iRequestCategoryByItsID() throws Exception {
        resultActions = mockMvc.perform(get(CATEGORY_ENDPOINT+"/{id}", category.getId()))
                .andExpect(status().isOk());
    }

    @Then("I get details of requested category")
    public void iGetDetailsOfRequestedCategory() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").exists(),
                        jsonPath("$.name").value(category.getName()));
    }

    // Update exist category

    @And("I prepared update category data")
    public void iPreparedUpdateCategoryData() {
        updateCategoryRequest = UpdateCategoryRequest
                .builder()
                .name(getCategoryName2)
                .build();
    }

    @When("I update category")
    public void iUpdateCategory() throws Exception {
        final var content = objectMapper.writeValueAsString(updateCategoryRequest);
        resultActions = mockMvc.perform(put(CATEGORY_ENDPOINT+"/{id}", category.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
        category = categoryRepository.findById(category.getId()).orElse(null);
    }

    @Then("I see updated category")
    public void iSeeUpdatedCategory() throws Exception {
        resultActions.andExpect(status().isNoContent());
        assertEquals(category.getName(), getCategoryName2);
    }

    // delete existing category

    @When("I delete the category")
    public void iDeleteTheCategory() throws Exception {
        resultActions = mockMvc.perform(delete(CATEGORY_ENDPOINT+"/{id}", category.getId()))
                .andExpect(status().isNoContent());
    }

    @Then("I dont see the category anymore")
    public void iDontSeeTheCategoryAnymore() throws Exception {
        mockMvc.perform(get(CATEGORY_ENDPOINT+"/{id}", category.getId()))
                .andExpect(status().isNotFound());
    }
}
