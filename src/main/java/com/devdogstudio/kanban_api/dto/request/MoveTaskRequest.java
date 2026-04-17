package com.devdogstudio.kanban_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class MoveTaskRequest {

    @NotNull(message = "Coluna destino é obrigatória")
    private UUID targetColumnId;

    @NotNull(message = "Posição é obrigatória")
    private Integer position;
}
