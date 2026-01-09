package org.example.dormtaskmanagerapi.entity.repository;

import org.example.dormtaskmanagerapi.entity.Room;
import org.example.dormtaskmanagerapi.entity.Task;
import org.example.dormtaskmanagerapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsByRoom_Id(Long roomId);
    boolean existsByUser_Id(Long userId);
    Page<Task> findByRoom(Room room, Pageable pageable);
    Page<Task> findByUser_Id(Long userId, Pageable pageable);
    Page<Task> findByUser(User currentUser, Pageable pageable);
}
