package com.testowanie.football.web;

import com.testowanie.football.dto.request.CreateEditorRequest;
import com.testowanie.football.dto.request.UpdateEditorRequest;
import com.testowanie.football.dto.resource.EditorResource;
import com.testowanie.football.service.EditorUseCases;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/editors")
public class EditorController {

    private EditorUseCases editorUseCases;

    @GetMapping
    public ResponseEntity<Page<EditorResource>> getEditors(@SortDefault(sort = "surname", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(editorUseCases.getEditors(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditorResource> getEditorById(@PathVariable Long id) {
        return ResponseEntity.ok(editorUseCases.getEditorById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createEditor(@Valid @RequestBody CreateEditorRequest createEditorRequest) {
        editorUseCases.createEditor(createEditorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateEditor(@PathVariable Long id, @Valid @RequestBody UpdateEditorRequest updateEditorRequest) {
        editorUseCases.updateEditor(id, updateEditorRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEditor(@PathVariable Long id) {
        editorUseCases.deleteEditor(id);
        return ResponseEntity.noContent().build();
    }
}
