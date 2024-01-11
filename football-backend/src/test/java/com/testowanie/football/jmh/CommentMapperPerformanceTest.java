package com.testowanie.football.jmh;

import com.testowanie.football.FootballApplication;
import com.testowanie.football.dto.request.CreateCommentRequest;
import com.testowanie.football.mapper.CommentMapper;
import com.testowanie.football.model.Comment;
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
public class CommentMapperPerformanceTest {
    private ConfigurableApplicationContext context;

    private CommentMapper mapper;
    private Comment comment;
    private CreateCommentRequest commentRequest;

    @Test
    public void contextLoads() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CommentMapperPerformanceTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        this.context = new SpringApplication(FootballApplication.class).run();

        mapper = this.context.getBean(CommentMapper.class);
    }

    @TearDown
    public void tearDown() {
        this.context.close();
    }


    @Benchmark
    @Warmup(iterations = 0)
    public void mapCommentToCommentResourceBenchmark() {
        comment = Comment.builder()
                .id(1L)
                .nickname("nickname")
                .content("content")
                .build();

        mapper.toCommentResource(comment);
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void mapCreateCommentRequestToCommentBenchmark() {
        commentRequest = CreateCommentRequest.builder()
                .nickname("nickname")
                .content("content")
                .build();

        mapper.fromCreateCommentRequest(commentRequest);
    }
}
