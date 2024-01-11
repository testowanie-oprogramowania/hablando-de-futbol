package com.testowanie.football.jmh;

import com.testowanie.football.FootballApplication;
import com.testowanie.football.dto.request.CreateCategoryRequest;
import com.testowanie.football.dto.request.UpdateCategoryRequest;
import com.testowanie.football.mapper.CategoryMapper;
import com.testowanie.football.model.Category;
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
public class CategoryMapperPerformanceTest {
    private ConfigurableApplicationContext context;

    private CategoryMapper mapper;
    private Category category;
    private CreateCategoryRequest categoryRequest;
    private UpdateCategoryRequest updateCategoryRequest;

    @Test
    public void contextLoads() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CategoryMapperPerformanceTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        this.context = new SpringApplication(FootballApplication.class).run();

        mapper = this.context.getBean(CategoryMapper.class);
    }

    @TearDown
    public void tearDown() {
        this.context.close();
    }


    @Benchmark
    @Warmup(iterations = 0)
    public void mapCategoryToCategoryResourceBenchmark() {
        category = Category.builder()
                .id(1L)
                .name("name")
                .build();

        mapper.toCategoryResource(category);
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void mapCreateCategoryRequestToCategoryBenchmark() {
        categoryRequest = new CreateCategoryRequest("name");

        mapper.fromCreateCategoryRequest(categoryRequest);
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void mapUpdateCategoryRequestToCategoryBenchmark() {
        updateCategoryRequest = UpdateCategoryRequest.builder().name("newName").build();
        category = Category.builder()
                .id(1L)
                .name("name")
                .build();

        mapper.updateCategoryFromUpdateCategoryRequest(updateCategoryRequest, category);
    }
}
