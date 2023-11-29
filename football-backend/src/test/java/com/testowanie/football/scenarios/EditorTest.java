package com.testowanie.football.scenarios;

import com.testowanie.football.dto.request.UpdateEditorRequest;
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
public class EditorTest {
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
    private UpdateEditorRequest updateEditorRequest;

    private final String EDITORS_ENDPOINT="/api/v1/editors";

    @Before
    public void setUpEditor() {
        editorRepository.deleteAll();
        categoryRepository.deleteAll();
        category = categoryRepository.save(new Category(exampleId, "La Liga", new HashSet<Article>()));
        articleRepository.deleteAll();
    }

    // Feature: post editor
    @Given("I have editor data prepared")
    public void iHaveEditorDataPrepared() {
        editor = editorRepository.save(new Editor(exampleId,"Zbyszek","JSON", null));
    }

    @When("I create editor profile")
    public void iCreateEditorProfile() throws Exception {
        var editorData = objectMapper.writeValueAsString(editor);

        resultActions = mockMvc.perform(post(EDITORS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(editorData));
    }

    @When("I list all editors")
    public void iListAllEditors() throws Exception {
        resultActions = mockMvc.perform(get(EDITORS_ENDPOINT+"/1"));
    }

    @Then("I see new editor")
    public void iSeeNewEditor() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(editor.getName()))
                .andExpect(jsonPath("$.surname").value(editor.getSurname()));
    }

    // get existing editor

    @When("I request an editor by its id")
    public void iRequestAnEditorByItsId() throws Exception {
        resultActions = mockMvc.perform(get(EDITORS_ENDPOINT+"/{id}",editor.getId()))
                .andExpect(status().isOk());
    }

    @Then("I get details of requested editor")
    public void iGetDetailsOfRequestedArticle() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").exists(),
                        jsonPath("$.name").value(editor.getName()),
                        jsonPath("$.surname").value(editor.getSurname()));
    }

    // Update existing article

    @And("I have an update for the editor")
    public void iHaveAnUpdateForTheArticle() {
        updateEditorRequest = UpdateEditorRequest
                .builder()
                .name("twoja stara")
                .surname("chuj")
                .build();
    }

    @When("I update the editor")
    public void iUpdateTheEditor() throws Exception {
        var updatedInfo = objectMapper.writeValueAsString(updateEditorRequest);
        resultActions = mockMvc.perform(put(EDITORS_ENDPOINT+"/{id}", editor.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedInfo));
        editor = editorRepository.findById(editor.getId()).orElse(null);
    }

    @Then("I see updated editor")
    public void iSeeUpdatedArticle() throws Exception {
        resultActions.andExpect(status().isNoContent());
        assertEquals(editor.getSurname(), "chuj");
    }

    // delete existing editor

    @When("I delete the editor")
    public void iDeleteTheArticle() throws Exception {
        resultActions = mockMvc.perform(delete(EDITORS_ENDPOINT+"/{id}", editor.getId()))
                .andExpect(status().isNoContent());
    }

    @Then("I dont see the editor anymore")
    public void iDontSeeTheEditorAnymore() throws Exception {
        mockMvc.perform(get(EDITORS_ENDPOINT+"/{id}", editor.getId()))
                .andExpect(status().isNotFound());
    }
}
