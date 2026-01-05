package org.example.dormtaskmanagerapi.entity.repository;

import org.example.dormtaskmanagerapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByRoomId(Long roomId);

    List<User> findByRoomId(Long id);
}
