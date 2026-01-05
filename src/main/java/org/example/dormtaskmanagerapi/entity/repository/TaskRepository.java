package org.example.dormtaskmanagerapi.entity.repository;

import org.example.dormtaskmanagerapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
    boolean existsByRoomId(Long roomId);
    boolean existsByUserId(Long userId);
}
