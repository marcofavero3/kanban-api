package com.devdogstudio.kanban_api.service;

import com.devdogstudio.kanban_api.dto.request.BoardRequest;
import com.devdogstudio.kanban_api.dto.response.BoardResponse;
import com.devdogstudio.kanban_api.entity.Board;
import com.devdogstudio.kanban_api.entity.User;
import com.devdogstudio.kanban_api.exception.ResourceNotFoundException;
import com.devdogstudio.kanban_api.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardResponse> findAll() {
        User user = getAuthenticatedUser();
        return boardRepository.findByUserId(user.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public BoardResponse findById(UUID id) {
        User user = getAuthenticatedUser();
        Board board = boardRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Board não encontrado"));
        return toResponse(board);
    }

    public BoardResponse create(BoardRequest request) {
        User user = getAuthenticatedUser();
        Board board = Board.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .user(user)
                .build();
        return toResponse(boardRepository.save(board));
    }

    public BoardResponse update(UUID id, BoardRequest request) {
        User user = getAuthenticatedUser();
        Board board = boardRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Board não encontrado"));
        board.setTitle(request.getTitle());
        board.setDescription(request.getDescription());
        return toResponse(boardRepository.save(board));
    }

    public void delete(UUID id) {
        User user = getAuthenticatedUser();
        Board board = boardRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Board não encontrado"));
        board.softDelete();
        boardRepository.save(board);
    }

    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private BoardResponse toResponse(Board board) {
        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .description(board.getDescription())
                .createdAt(board.getCreatedAt())
                .build();
    }
}
