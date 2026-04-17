package com.devdogstudio.kanban_api.repository;

import com.devdogstudio.kanban_api.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardRepository extends JpaRepository<Board, UUID> {
    List<Board> findByUserId(UUID userId);
    Optional<Board> findByIdAndUserId(UUID id, UUID userId);
}
