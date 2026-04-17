package com.devdogstudio.kanban_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    private UUID id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Integer position;
    private UUID columnId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
