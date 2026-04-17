package com.devdogstudio.kanban_api.controller;

import com.devdogstudio.kanban_api.dto.request.CommentRequest;
import com.devdogstudio.kanban_api.dto.response.CommentResponse;
import com.devdogstudio.kanban_api.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks/{taskId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponse>> findAll(@PathVariable UUID taskId) {
        return ResponseEntity.ok(commentService.findAll(taskId));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(
            @PathVariable UUID taskId,
            @RequestBody @Valid CommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(taskId, request));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID taskId,
            @PathVariable UUID commentId) {
        commentService.delete(taskId, commentId);
        return ResponseEntity.noContent().build();
    }
}
