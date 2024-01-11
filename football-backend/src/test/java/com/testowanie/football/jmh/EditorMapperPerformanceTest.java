package com.testowanie.football.jmh;

import com.testowanie.football.FootballApplication;
import com.testowanie.football.dto.request.CreateEditorRequest;
import com.testowanie.football.dto.request.UpdateEditorRequest;
import com.testowanie.football.mapper.EditorMapper;
import com.testowanie.football.model.Editor;
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
public class EditorMapperPerformanceTest {
    private ConfigurableApplicationContext context;

    private EditorMapper mapper;
    private Editor editor;
    private CreateEditorRequest editorRequest;
    private UpdateEditorRequest updateEditorRequest;

    @Test
    public void contextLoads() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(EditorMapperPerformanceTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        this.context = new SpringApplication(FootballApplication.class).run();

        mapper = this.context.getBean(EditorMapper.class);
    }

    @TearDown
    public void tearDown() {
        this.context.close();
    }


    @Benchmark
    @Warmup(iterations = 0)
    public void mapEditorToEditorResourceBenchmark() {
        editor = Editor.builder()
                .id(1L)
                .name("name")
                .surname("surname")
                .photoUrl("photoUrl")
                .build();

        mapper.toEditorResource(editor);
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void mapCreateEditorRequestToEditorBenchmark() {
        editorRequest = CreateEditorRequest.builder()
                .name("name")
                .surname("surname")
                .photoUrl("photoUrl")
                .build();

        mapper.createEditorRequestToEditor(editorRequest);
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void mapUpdateEditorRequestToEditorBenchmark() {
        updateEditorRequest = UpdateEditorRequest.builder()
                .name("name")
                .surname("surname")
                .photoUrl("photoUrl")
                .build();
        editor = Editor.builder()
                .id(1L)
                .name("different_name")
                .surname("different_surname")
                .photoUrl("different_photoUrl")
                .build();

        mapper.updateEditorFromUpdateEditorRequest(updateEditorRequest, editor);
    }
}
