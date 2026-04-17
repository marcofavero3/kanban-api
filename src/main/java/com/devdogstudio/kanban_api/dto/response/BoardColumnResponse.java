package com.devdogstudio.kanban_api.dto.response;

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
public class BoardColumnResponse {

    private UUID id;
    private String title;
    private Integer position;
    private UUID boardId;
    private LocalDateTime createdAt;
}
