package com.testowanie.football.jmh;

import com.testowanie.football.FootballApplication;
import com.testowanie.football.dto.request.CreateArticleRequest;
import com.testowanie.football.dto.request.UpdateArticleRequest;
import com.testowanie.football.mapper.ArticleMapper;
import com.testowanie.football.model.Article;
import com.testowanie.football.model.Category;
import com.testowanie.football.model.Editor;
import com.testowanie.football.repository.CategoryRepository;
import com.testowanie.football.repository.EditorRepository;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class ArticleMapperPerformanceTest {
    private ConfigurableApplicationContext context;

    private ArticleMapper mapper;
    private Article article;
    private CreateArticleRequest articleRequest;
    private UpdateArticleRequest updateArticleRequest;

    private EditorRepository editorRepository;
    private CategoryRepository categoryRepository;
    private Editor editor;
    private Category category;

    @Test
    public void contextLoads() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ArticleMapperPerformanceTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        this.context = new SpringApplication(FootballApplication.class).run();

        mapper = this.context.getBean(ArticleMapper.class);
        editorRepository = this.context.getBean(EditorRepository.class);
        categoryRepository = this.context.getBean(CategoryRepository.class);

        category = categoryRepository.save(Category.builder().name("abc").build());
        editor = editorRepository.save(Editor.builder()
                .name("name")
                .surname("surname")
                .photoUrl("photoUrl")
                .build());
    }

    @TearDown
    public void tearDown() {
        this.context.close();
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void mapArticleToArticleResourceBenchmark() {
        article = Article.builder()
                .id(1L)
                .title("title")
                .editor(editor)
                .createdDate(null)
                .lastModifiedDate(null)
                .content("content")
                .photoUrl("image")
                .category(category)
                .build();

        mapper.toArticleResource(article);
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void mapCreateArticleRequestToArticleBenchmark() {
        articleRequest = CreateArticleRequest.builder()
                .title("title")
                .editorId(editor.getId())
                .content("content")
                .photoUrl("image")
                .categoryName(category.getName())
                .build();

        mapper.fromCreateArticleRequest(articleRequest);
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void mapUpdateArticleRequestToArticleBenchmark() {
        updateArticleRequest = UpdateArticleRequest.builder()
                .title("new")
                .editorId(editor.getId())
                .content("new")
                .photoUrl("new")
                .categoryName(category.getName())
                .build();
        article = Article.builder()
                .id(1L)
                .title("title")
                .editor(editor)
                .createdDate(null)
                .lastModifiedDate(null)
                .content("content")
                .photoUrl("image")
                .category(category)
                .build();

        mapper.updateArticleFromUpdateArticleRequest(updateArticleRequest, article);
    }
}
