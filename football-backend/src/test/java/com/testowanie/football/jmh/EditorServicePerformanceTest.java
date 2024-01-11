package com.testowanie.football.jmh;

import com.testowanie.football.FootballApplication;
import com.testowanie.football.dto.request.CreateEditorRequest;
import com.testowanie.football.dto.request.UpdateEditorRequest;
import com.testowanie.football.exception.EntityNotFoundException;
import com.testowanie.football.model.Editor;
import com.testowanie.football.repository.EditorRepository;
import com.testowanie.football.service.EditorUseCases;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Pageable;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
public class EditorServicePerformanceTest {
    private ConfigurableApplicationContext context;

    private EditorUseCases editorUseCases;
    private EditorRepository editorRepository;
    private Editor editor;
    private Editor editorToDelete;
    private Editor editorToUpdate;

    @Test
    public void contextLoads() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(EditorServicePerformanceTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        this.context = new SpringApplication(FootballApplication.class).run();

        editorUseCases = this.context.getBean(EditorUseCases.class);
        editorRepository = this.context.getBean(EditorRepository.class);
        editorRepository.deleteAll();

        final var editor0 = Editor.builder()
                .name("name")
                .surname("surname")
                .build();
        editor = editorRepository.saveAndFlush(editor0);

        final var editor1 = Editor.builder()
                .name("name1")
                .surname("surname1")
                .build();
        editorToDelete = editorRepository.saveAndFlush(editor1);

        final var editor2 = Editor.builder()
                .name("name2")
                .surname("surname2")
                .build();
        editorToUpdate = editorRepository.saveAndFlush(editor2);


    }

    @TearDown
    public void tearDown() {
        this.context.close();
    }


    @Benchmark
    @Warmup(iterations = 0)
    public void getEditorsBenchmark() {
        editorUseCases.getEditors(Pageable.ofSize(10));
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void getEditorByIdBenchmark() {
        editorUseCases.getEditorById(editor.getId());
    }

    @Benchmark
    @Warmup(iterations = 0)
    @Measurement(iterations = 1)
    public void deleteEditorBenchmark() {
        try {
            editorUseCases.deleteEditor(editorToDelete.getId());
        } catch (EntityNotFoundException ignored) {
        }
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void updateEditorBenchmark() {
        final var updateEditorRequest = UpdateEditorRequest.builder()
                .name("name" + System.currentTimeMillis())
                .surname("surname" + System.currentTimeMillis())
                .photoUrl("photoUrl")
                .build();

        editorUseCases.updateEditor(editorToUpdate.getId(), updateEditorRequest);
    }

    @Benchmark
    @Warmup(iterations = 0)
    public void createEditorBenchmark() {
        final var createEditorRequest = CreateEditorRequest.builder()
                .name("name" + System.currentTimeMillis())
                .surname("surname" + System.currentTimeMillis())
                .photoUrl("photoUrl")
                .build();

        editorUseCases.createEditor(createEditorRequest);
    }
}
