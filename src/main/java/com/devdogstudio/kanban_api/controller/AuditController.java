package com.devdogstudio.kanban_api.controller;


import com.devdogstudio.kanban_api.dto.response.AuditLogResponse;
import com.devdogstudio.kanban_api.entity.AuditLog;
import com.devdogstudio.kanban_api.entity.User;
import com.devdogstudio.kanban_api.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditLogRepository auditLogRepository;

    @GetMapping
    public ResponseEntity<List<AuditLogResponse>> findAll() {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        List<AuditLogResponse> logs = auditLogRepository
                .findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(logs);
    }

    private AuditLogResponse toResponse(AuditLog log) {
        return AuditLogResponse.builder()
                .id(log.getId())
                .action(log.getAction())
                .entityType(log.getEntityType())
                .entityId(log.getEntityId())
                .createdAt(log.getCreatedAt())
                .build();
    }
}