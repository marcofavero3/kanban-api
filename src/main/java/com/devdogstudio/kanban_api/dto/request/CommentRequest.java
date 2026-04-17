package com.devdogstudio.kanban_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequest {

    @NotBlank(message = "Conteúdo é obrigatório")
    private String content;
}
