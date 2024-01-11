package com.testowanie.football.jmh;

import com.testowanie.football.FootballApplication;
import com.testowanie.football.dto.request.CreateCategoryRequest;
import com.testowanie.football.dto.request.UpdateCategoryRequest;
import com.testowanie.football.exception.EntityNotFoundException;
import com.testowanie.football.model.Category;
import com.testowanie.football.repository.CategoryRepository;
import com.testowanie.football.service.CategoryUseCases;
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
public class CategoryServicePerformanceTest {
    private ConfigurableApplicationContext context;

    private CategoryUseCases categoryUseCases;
    private CategoryRepository categoryRepository;
    private Category category;
    private Category categoryToDelete;
    private Category categoryToUpdate;

    @Test
    public void contextLoads() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CategoryServicePerformanceTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        this.context = new SpringApplication(FootballApplication.class).run();

        categoryUseCases = this.context.getBean(CategoryUseCases.class);
        categoryRepository = this.context.getBean(CategoryRepository.class);
        categoryRepository.deleteAll();

        final var category0 = Category.builder()
                .name("name")
                .build();
        category = categoryRepository.saveAndFlush(category0);

        final var category1 = Category.builder()
                .name("name1")
                .build();
        categoryToDelete = categoryRepository.saveAndFlush(category1);

        final var category2 = Category.builder()
                .name("name2")
                .build();
        categoryToUpdate = categoryRepository.saveAndFlush(category2);


    }

    @TearDown
    public void tearDown() {
        this.context.close();
    }


    @Benchmark
    @Warmup(iterations = 0)
    public void getCategoriesBenchmark() {
        categoryUseCases.findAll();
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void getCategoryByIdBenchmark() {
        categoryUseCases.findCategoryById(1L);
    }

    @Benchmark
    @Warmup(iterations = 0)
    @Measurement(iterations = 1)
    public void deleteCategoryBenchmark() {
        try {
            categoryUseCases.deleteCategory(categoryToDelete.getId());
        } catch (EntityNotFoundException ignored) {
        }
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void updateCategoryBenchmark() {
        final var updateCategoryRequest = UpdateCategoryRequest.builder()
                .name("name" + System.currentTimeMillis())
                .build();

        categoryUseCases.updateCategory(categoryToUpdate.getId(), updateCategoryRequest);
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void createCategoryBenchmark() {
        final var createCategoryRequest = new CreateCategoryRequest("name" + System.currentTimeMillis());

        categoryUseCases.createCategory(createCategoryRequest);
    }
}
