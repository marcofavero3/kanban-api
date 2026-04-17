package com.devdogstudio.kanban_api.repository;

import com.devdogstudio.kanban_api.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByTaskIdOrderByCreatedAtDesc(UUID taskId);
    Optional<Comment> findByIdAndUserId(UUID id, UUID userId);
}
