package com.devdogstudio.kanban_api.repository;

import com.devdogstudio.kanban_api.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByColumnIdOrderByPosition(UUID columnId);
    Optional<Task> findByIdAndColumnId(UUID id, UUID columnId);
}
