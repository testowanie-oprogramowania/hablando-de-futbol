package com.testowanie.football.web;

import com.testowanie.football.model.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody Comment comment) {
        return ResponseEntity.created(null).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable long id) {
        return ResponseEntity.noContent().build();
    }
}
