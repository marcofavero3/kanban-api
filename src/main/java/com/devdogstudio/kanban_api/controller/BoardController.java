package com.devdogstudio.kanban_api.controller;

import com.devdogstudio.kanban_api.dto.request.BoardRequest;
import com.devdogstudio.kanban_api.dto.response.BoardResponse;
import com.devdogstudio.kanban_api.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<BoardResponse>> findAll() {
        return ResponseEntity.ok(boardService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(boardService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BoardResponse> create(@RequestBody @Valid BoardRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> update(@PathVariable UUID id,
                                                @RequestBody @Valid BoardRequest request) {
        return ResponseEntity.ok(boardService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
