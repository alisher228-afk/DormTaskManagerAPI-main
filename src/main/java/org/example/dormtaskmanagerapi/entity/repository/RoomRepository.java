package org.example.dormtaskmanagerapi.entity.repository;

import org.example.dormtaskmanagerapi.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
