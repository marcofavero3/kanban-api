package com.devdogstudio.kanban_api.audit;

import com.devdogstudio.kanban_api.entity.AuditLog;
import com.devdogstudio.kanban_api.entity.User;
import com.devdogstudio.kanban_api.enums.AuditAction;
import com.devdogstudio.kanban_api.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public void log(AuditAction action, String entityType, UUID entityId) {
        User user = getAuthenticatedUser();

        AuditLog log = AuditLog.builder()
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .user(user)
                .build();

        auditLogRepository.save(log);
    }

    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
