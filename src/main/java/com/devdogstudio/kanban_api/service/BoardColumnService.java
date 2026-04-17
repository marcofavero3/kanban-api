package com.devdogstudio.kanban_api.service;

import com.devdogstudio.kanban_api.dto.request.BoardColumnRequest;
import com.devdogstudio.kanban_api.dto.response.BoardColumnResponse;
import com.devdogstudio.kanban_api.entity.Board;
import com.devdogstudio.kanban_api.entity.BoardColumn;
import com.devdogstudio.kanban_api.entity.User;
import com.devdogstudio.kanban_api.exception.ResourceNotFoundException;
import com.devdogstudio.kanban_api.repository.BoardColumnRepository;
import com.devdogstudio.kanban_api.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardColumnService {

    private final BoardColumnRepository boardColumnRepository;
    private final BoardRepository boardRepository;

    public List<BoardColumnResponse> findAll(UUID boardId) {
        getBoard(boardId);
        return boardColumnRepository.findByBoardIdOrderByPosition(boardId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public BoardColumnResponse findById(UUID boardId, UUID columnId) {
        getBoard(boardId);
        BoardColumn column = boardColumnRepository.findByIdAndBoardId(columnId, boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Coluna não encontrada"));
        return toResponse(column);
    }

    public BoardColumnResponse create(UUID boardId, BoardColumnRequest request) {
        Board board = getBoard(boardId);
        BoardColumn column = BoardColumn.builder()
                .title(request.getTitle())
                .position(request.getPosition())
                .board(board)
                .build();
        return toResponse(boardColumnRepository.save(column));
    }

    public BoardColumnResponse update(UUID boardId, UUID columnId, BoardColumnRequest request) {
        getBoard(boardId);
        BoardColumn column = boardColumnRepository.findByIdAndBoardId(columnId, boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Coluna não encontrada"));
        column.setTitle(request.getTitle());
        column.setPosition(request.getPosition());
        return toResponse(boardColumnRepository.save(column));
    }

    public void delete(UUID boardId, UUID columnId) {
        getBoard(boardId);
        BoardColumn column = boardColumnRepository.findByIdAndBoardId(columnId, boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Coluna não encontrada"));
        column.softDelete();
        boardColumnRepository.save(column);
    }

    private Board getBoard(UUID boardId) {
        User user = getAuthenticatedUser();
        return boardRepository.findByIdAndUserId(boardId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Board não encontrado"));
    }

    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private BoardColumnResponse toResponse(BoardColumn column) {
        return BoardColumnResponse.builder()
                .id(column.getId())
                .title(column.getTitle())
                .position(column.getPosition())
                .boardId(column.getBoard().getId())
                .createdAt(column.getCreatedAt())
                .build();
    }
}
