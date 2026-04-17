package com.devdogstudio.kanban_api.controller;

import com.devdogstudio.kanban_api.dto.request.MoveTaskRequest;
import com.devdogstudio.kanban_api.dto.request.TaskRequest;
import com.devdogstudio.kanban_api.dto.response.TaskResponse;
import com.devdogstudio.kanban_api.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/boards/{boardId}")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/columns/{columnId}/tasks")
    public ResponseEntity<List<TaskResponse>> findAll(
            @PathVariable UUID boardId,
            @PathVariable UUID columnId) {
        return ResponseEntity.ok(taskService.findAll(boardId, columnId));
    }

    @GetMapping("/columns/{columnId}/tasks/{taskId}")
    public ResponseEntity<TaskResponse> findById(
            @PathVariable UUID boardId,
            @PathVariable UUID columnId,
            @PathVariable UUID taskId) {
        return ResponseEntity.ok(taskService.findById(boardId, columnId, taskId));
    }

    @PostMapping("/columns/{columnId}/tasks")
    public ResponseEntity<TaskResponse> create(
            @PathVariable UUID boardId,
            @PathVariable UUID columnId,
            @RequestBody @Valid TaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(boardId, columnId, request));
    }

    @PutMapping("/columns/{columnId}/tasks/{taskId}")
    public ResponseEntity<TaskResponse> update(
            @PathVariable UUID boardId,
            @PathVariable UUID columnId,
            @PathVariable UUID taskId,
            @RequestBody @Valid TaskRequest request) {
        return ResponseEntity.ok(taskService.update(boardId, columnId, taskId, request));
    }

    @PatchMapping("/tasks/{taskId}/move")
    public ResponseEntity<TaskResponse> move(
            @PathVariable UUID boardId,
            @PathVariable UUID taskId,
            @RequestBody @Valid MoveTaskRequest request) {
        return ResponseEntity.ok(taskService.move(boardId, taskId, request));
    }

    @DeleteMapping("/columns/{columnId}/tasks/{taskId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID boardId,
            @PathVariable UUID columnId,
            @PathVariable UUID taskId) {
        taskService.delete(boardId, columnId, taskId);
        return ResponseEntity.noContent().build();
    }
}
