package com.devdogstudio.kanban_api.dto.response;

import com.devdogstudio.kanban_api.enums.AuditAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse {

    private UUID id;
    private AuditAction action;
    private String entityType;
    private UUID entityId;
    private LocalDateTime createdAt;
}
