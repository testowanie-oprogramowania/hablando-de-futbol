//package com.testowanie.football.scenarios;
//
//import com.testowanie.football.dto.request.UpdateArticleRequest;
//import com.testowanie.football.model.Article;
//import com.testowanie.football.model.Category;
//import com.testowanie.football.model.Comment;
//import com.testowanie.football.model.Editor;
//import com.testowanie.football.repository.ArticleRepository;
//import io.cucumber.java.Before;
//import io.cucumber.java.en.And;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.util.Objects;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//public class CommentTest {
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ArticleRepository articleRepository;
//
//    private String originalTitle = "CR7 is the GOAT";
//    private String commentContent = "Siuuuu";
//    private Integer likes = 2137;
//    private String nickname = "Kardo93442";
//    private final String ARTICLES_ENDPOINT="/articles/";
//    private final String COMMENTS_ENDPOINT="/comments";
//
//    private Long exampleId=1L;
//
//    private Long articleId;
//    private Long commentId;
//    private Article article;
//    private Comment comment;
//
//
//    private ResultActions resultActions;
//    private UpdateArticleRequest updateArticleRequest;
//
//    @Before
//    public void setUp() {
//        articleRepository.deleteAll();
//        article = Article.builder()
//                .title(originalTitle)
//                .editor(new Editor(exampleId,"Zbyszek","JSON", null))
//                .content("chuj")
//                .category(new Category(exampleId, "dupa romana", null))
//                .build();
//        articleId = article.getId();
//        articleRepository.save(article);
//
//    }
//
//    // post comment
//    @Given("I have a comment for the article")
//    public void iHaveACommentForTheArticle() {
//        setUp();
//        comment = Comment.builder()
//                .content(commentContent)
//                .thumbsUp(likes)
//                .nickname(nickname)
//                .build();
//    }
//
//    @When("I post a comment")
//    public void iPostAComment() throws Exception {
//        var content = objectMapper.writeValueAsString(comment);
//        resultActions = mockMvc.perform(post(ARTICLES_ENDPOINT+"/{id}/"+COMMENTS_ENDPOINT, articleId)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(content));
//    }
//
//    @Then("I see it in the comment section")
//    public void iSeeItInTheCommentSection() throws Exception {
//        resultActions.andExpect(status().isCreated());
//    }
//
//    // delete comment
//
//    @And("I deleted a comment")
//    public void iDeletedAComment() throws Exception {
//        commentId = articleRepository.findById(articleId).get()
//                .getComments().stream().findAny().get().getId();
//
//        resultActions = mockMvc.perform(delete(ARTICLES_ENDPOINT+"/{id}/"+COMMENTS_ENDPOINT+"/{commentId}",
//                articleId, commentId));
//    }
//
//    @Then("I dont see the comment")
//    public void iDontSeeTheComment() throws Exception {
//        resultActions.andExpect(status().isNoContent());
//        var commentExists = articleRepository.findById(articleId).get()
//                .getComments().stream()
//                .anyMatch(com -> Objects.equals(com.getId(), commentId));
//        assertFalse(commentExists);
//    }
//
//    // like comment
//    @When("I like a comment")
//    public void iLikeAComment() {
//        var currLikes = comment.getThumbsUp();
//        comment.setThumbsUp(currLikes+1);
//
//        // updateArticleRequest = UpdateArticleRequest.builder()
//    }
//
//    @Then("I see number of likes up by 1")
//    public void iSeeNumberOfLikesUpBy1() {
//        // pass
//    }
//
//    @When("I dislike a comment")
//    public void iDislikeAComment() {
//        var currDislikes = comment.getThumbsDown();
//        comment.setThumbsDown(currDislikes+1);
//
//        // updateArticleRequest = UpdateArticleRequest.builder()
//    }
//
//
//    @Then("I see number of dislikes up by 1")
//    public void iSeeNumberOfDisLikesUpBy1() {
//        // pass
//    }
//
//    @When("I change my mind from like to dislike")
//    public void iChangeMyMindFromLikeToDislike() {
//        var currLikes = comment.getThumbsUp();
//        comment.setThumbsUp(currLikes-1);
//
//        var currDislikes = comment.getThumbsDown();
//        comment.setThumbsDown(currDislikes+1);
//
//        // updateArticleRequest = UpdateArticleRequest.builder()
//    }
//
//    @Then("I see likes down and dislikes up by 1")
//    public void iSeeLikesDownAndDislikeUpBy1() {
//
//    }
//
//    @When("I change my mind from dislike to like")
//    public void iChangeMyMindFromDisLikeToLike() {
//        var currDisLikes = comment.getThumbsDown();
//        comment.setThumbsUp(currDisLikes-1);
//
//        var currLikes = comment.getThumbsUp();
//        comment.setThumbsDown(currLikes+1);
//
//        // updateArticleRequest = UpdateArticleRequest.builder()
//    }
//
//    @Then("I see dislikes down and likes up by 1")
//    public void iSeeDislikesDownAndLikesUpBy1() {
//
//    }
//
//    @When("I don want like anymore")
//    public void iDontWantLikeAnymore() {
//        var currLikes = comment.getThumbsUp();
//        comment.setThumbsDown(currLikes-1);
//
//        //
//    }
//
//    @Then("I see likes down by 1")
//    public void iSeeLikesDownBy1() {
//        //
//    }
//
//    @When("I don want dislike anymore")
//    public void iDontWantDisLikeAnymore() {
//        var currDisLikes = comment.getThumbsUp();
//        comment.setThumbsDown(currDisLikes-1);
//
//        //
//    }
//    @Then("I see dislikes down by 1")
//    public void iSeeDisLikesDownBy1() {
//        //
//    }
//
//
//}
