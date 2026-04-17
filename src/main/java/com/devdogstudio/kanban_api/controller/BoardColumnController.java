package com.devdogstudio.kanban_api.controller;

import com.devdogstudio.kanban_api.dto.request.BoardColumnRequest;
import com.devdogstudio.kanban_api.dto.response.BoardColumnResponse;
import com.devdogstudio.kanban_api.service.BoardColumnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/boards/{boardId}/columns")
@RequiredArgsConstructor
public class BoardColumnController {

    private final BoardColumnService boardColumnService;

    @GetMapping
    public ResponseEntity<List<BoardColumnResponse>> findAll(@PathVariable UUID boardId) {
        return ResponseEntity.ok(boardColumnService.findAll(boardId));
    }

    @GetMapping("/{columnId}")
    public ResponseEntity<BoardColumnResponse> findById(
            @PathVariable UUID boardId,
            @PathVariable UUID columnId) {
        return ResponseEntity.ok(boardColumnService.findById(boardId, columnId));
    }

    @PostMapping
    public ResponseEntity<BoardColumnResponse> create(
            @PathVariable UUID boardId,
            @RequestBody @Valid BoardColumnRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardColumnService.create(boardId, request));
    }

    @PutMapping("/{columnId}")
    public ResponseEntity<BoardColumnResponse> update(
            @PathVariable UUID boardId,
            @PathVariable UUID columnId,
            @RequestBody @Valid BoardColumnRequest request) {
        return ResponseEntity.ok(boardColumnService.update(boardId, columnId, request));
    }

    @DeleteMapping("/{columnId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID boardId,
            @PathVariable UUID columnId) {
        boardColumnService.delete(boardId, columnId);
        return ResponseEntity.noContent().build();
    }
}
