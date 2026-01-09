package org.example.dormtaskmanagerapi.application.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dormtaskmanagerapi.application.Dto.RoomResponses.RoomListResponse;
import org.example.dormtaskmanagerapi.application.Dto.RoomResponses.RoomDetailResponse;
import org.example.dormtaskmanagerapi.application.Dto.UserResponses.UserShortResponse;
import org.example.dormtaskmanagerapi.application.mapper.RoomMapper;
import org.example.dormtaskmanagerapi.entity.Room;
import org.example.dormtaskmanagerapi.entity.User;
import org.example.dormtaskmanagerapi.entity.repository.AuthUserRepository;
import org.example.dormtaskmanagerapi.entity.repository.RoomRepository;
import org.example.dormtaskmanagerapi.entity.repository.TaskRepository;
import org.example.dormtaskmanagerapi.entity.repository.UserRepository;
import org.example.dormtaskmanagerapi.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("NullableProblems")
@Service
public class RoomService {

    public static final Logger log = LoggerFactory.getLogger(RoomService.class);

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final RoomMapper roomMapper;
    private final AuthUserRepository authUserRepository;
    private final SecurityService securityService;

    public RoomService(RoomRepository roomRepository, UserRepository userRepository, TaskRepository taskRepository, RoomMapper roomMapper, AuthUserRepository authUserRepository, SecurityService securityService) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.roomMapper = roomMapper;
        this.authUserRepository = authUserRepository;
        this.securityService = securityService;
    }

    public Room createRoom(Room room) {
        log.info("Creating room {}", room);
        roomRepository.save(room);
        return room;
    }

    public Page<RoomListResponse> getAllRooms(int page, int size) {
        log.info("Getting all rooms");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        return roomRepository.findAll(pageable)
                .map(roomMapper::toRoomListResponse);
    }

    public RoomDetailResponse getRoomById(Long id) {
        User currentUser = securityService.getCurrentUser();

        if (!securityService.isAdmin()) {

            if (currentUser.getRoom() == null) {
                throw new AccessDeniedException("User is not assigned to any room");
            }

            if (!currentUser.getRoom().getId().equals(id)) {
                throw new AccessDeniedException("Not your room");
            }
        }

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        List<UserShortResponse> users = userRepository.findByRoomId(id)
                .stream()
                .map(u -> new UserShortResponse(u.getId(), u.getName()))
                .toList();

        return new RoomDetailResponse(room.getId(), room.getName(), users
        );
    }


    public Room deleteRoomById(Long id) {
        log.info("Deleting room by id {}", id);
        Room room = roomRepository.findById( id)
                .orElseThrow(()->new EntityNotFoundException("Room with id " + id + " not found"));
        if(userRepository.existsByRoomId(id)) {
            throw  new IllegalStateException("cannot delete room with users");
        }
        if(taskRepository.existsByRoom_Id(id)) {
            throw  new IllegalStateException("cannot delete room with tasks");
        }
        roomRepository.delete(room);
        return room;
    }
}
