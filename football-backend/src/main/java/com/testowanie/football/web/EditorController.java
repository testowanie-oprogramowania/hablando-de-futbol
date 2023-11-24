package com.testowanie.football.web;

import com.testowanie.football.model.Editor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/editors")
public class EditorController {

    @GetMapping
    public ResponseEntity<Pageable> getEditors(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditorController> getEditorById(@PathVariable long id) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> createEditor(@RequestBody Editor editor) {
        return ResponseEntity.created(null).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateEditor(@PathVariable long id, @RequestBody Editor editor) {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEditor(@PathVariable long id) {
        return ResponseEntity.noContent().build();
    }
}
