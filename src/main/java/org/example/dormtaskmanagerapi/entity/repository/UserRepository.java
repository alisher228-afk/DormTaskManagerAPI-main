package org.example.dormtaskmanagerapi.entity.repository;

import org.example.dormtaskmanagerapi.entity.Room;
import org.example.dormtaskmanagerapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByRoomId(Long roomId);
    List<User> findByRoomId(Long id);
    Optional<User> findByAuthUser_Username(String username);

    Page<User> findByRoom(Room room, Pageable pageable);
}
