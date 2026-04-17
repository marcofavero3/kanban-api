package com.devdogstudio.kanban_api.service;

import com.devdogstudio.kanban_api.dto.request.MoveTaskRequest;
import com.devdogstudio.kanban_api.dto.request.TaskRequest;
import com.devdogstudio.kanban_api.dto.response.TaskResponse;
import com.devdogstudio.kanban_api.entity.BoardColumn;
import com.devdogstudio.kanban_api.entity.Task;
import com.devdogstudio.kanban_api.entity.User;
import com.devdogstudio.kanban_api.repository.BoardColumnRepository;
import com.devdogstudio.kanban_api.repository.BoardRepository;
import com.devdogstudio.kanban_api.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final BoardColumnRepository boardColumnRepository;
    private final BoardRepository boardRepository;

    public List<TaskResponse> findAll(UUID boardId, UUID columnId) {
        getColumn(boardId, columnId);
        return taskRepository.findByColumnIdOrderByPosition(columnId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TaskResponse findById(UUID boardId, UUID columnId, UUID taskId) {
        getColumn(boardId, columnId);
        Task task = taskRepository.findByIdAndColumnId(taskId, columnId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        return toResponse(task);
    }

    public TaskResponse create(UUID boardId, UUID columnId, TaskRequest request) {
        BoardColumn column = getColumn(boardId, columnId);
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .position(request.getPosition() != null ? request.getPosition() : 0)
                .column(column)
                .build();
        return toResponse(taskRepository.save(task));
    }

    public TaskResponse update(UUID boardId, UUID columnId, UUID taskId, TaskRequest request) {
        getColumn(boardId, columnId);
        Task task = taskRepository.findByIdAndColumnId(taskId, columnId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        if (request.getPosition() != null) {
            task.setPosition(request.getPosition());
        }
        return toResponse(taskRepository.save(task));
    }

    public TaskResponse move(UUID boardId, UUID taskId, MoveTaskRequest request) {
        User user = getAuthenticatedUser();
        boardRepository.findByIdAndUserId(boardId, user.getId())
                .orElseThrow(() -> new RuntimeException("Board não encontrado"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        BoardColumn targetColumn = boardColumnRepository.findByIdAndBoardId(
                        request.getTargetColumnId(), boardId)
                .orElseThrow(() -> new RuntimeException("Coluna destino não encontrada"));

        task.setColumn(targetColumn);
        task.setPosition(request.getPosition());
        return toResponse(taskRepository.save(task));
    }

    public void delete(UUID boardId, UUID columnId, UUID taskId) {
        getColumn(boardId, columnId);
        Task task = taskRepository.findByIdAndColumnId(taskId, columnId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        task.softDelete();
        taskRepository.save(task);
    }

    private BoardColumn getColumn(UUID boardId, UUID columnId) {
        User user = getAuthenticatedUser();
        boardRepository.findByIdAndUserId(boardId, user.getId())
                .orElseThrow(() -> new RuntimeException("Board não encontrado"));
        return boardColumnRepository.findByIdAndBoardId(columnId, boardId)
                .orElseThrow(() -> new RuntimeException("Coluna não encontrada"));
    }

    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .position(task.getPosition())
                .columnId(task.getColumn().getId())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
