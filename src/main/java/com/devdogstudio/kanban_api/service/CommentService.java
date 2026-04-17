package com.devdogstudio.kanban_api.service;

import com.devdogstudio.kanban_api.audit.AuditService;
import com.devdogstudio.kanban_api.dto.request.CommentRequest;
import com.devdogstudio.kanban_api.dto.response.CommentResponse;
import com.devdogstudio.kanban_api.entity.Comment;
import com.devdogstudio.kanban_api.entity.Task;
import com.devdogstudio.kanban_api.entity.User;
import com.devdogstudio.kanban_api.enums.AuditAction;
import com.devdogstudio.kanban_api.exception.ResourceNotFoundException;
import com.devdogstudio.kanban_api.repository.CommentRepository;
import com.devdogstudio.kanban_api.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final AuditService auditService;

    public List<CommentResponse> findAll(UUID taskId) {
        getTask(taskId);
        return commentRepository.findByTaskIdOrderByCreatedAtDesc(taskId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CommentResponse create(UUID taskId, CommentRequest request) {
        User user = getAuthenticatedUser();
        Task task = getTask(taskId);

        Comment comment = Comment.builder()
                .content(request.getContent())
                .task(task)
                .user(user)
                .build();

        Comment saved = commentRepository.save(comment);
        auditService.log(AuditAction.CREATED, "COMMENT", saved.getId());
        return toResponse(saved);
    }

    public void delete(UUID taskId, UUID commentId) {
        getTask(taskId);
        User user = getAuthenticatedUser();

        Comment comment = commentRepository.findByIdAndUserId(commentId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Comentário não encontrado"));

        comment.softDelete();
        commentRepository.save(comment);
        auditService.log(AuditAction.DELETED, "COMMENT", comment.getId());
    }

    private Task getTask(UUID taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));
    }

    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private CommentResponse toResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .taskId(comment.getTask().getId())
                .userName(comment.getUser().getName())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
