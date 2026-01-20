package org.example.dormtaskmanagerapi.application.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dormtaskmanagerapi.application.Dto.UserResponses.UserListResponse;
import org.example.dormtaskmanagerapi.entity.AuthUser;
import org.example.dormtaskmanagerapi.entity.User;
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
import org.springframework.stereotype.Service;


@Service
public class UserService {
    public static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final RoomRepository roomRepository;
    private final SecurityService securityService;

    public UserService(UserRepository userRepository, TaskRepository taskRepository, RoomRepository roomRepository, SecurityService securityService) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.roomRepository = roomRepository;
        this.securityService = securityService;
    }

    public User createUsers(User user) {
        log.info("Creating users");
        if(!roomRepository.existsById(user.getRoom().getId())) {
            throw new EntityNotFoundException("Room not found");
        }
        AuthUser authUser = new AuthUser();
        user.setAuthUser(authUser);
        userRepository.save(user);
        return user;
    }

    public Page<UserListResponse> getUsers(int page, int size) {
        User currentUser = securityService.getCurrentUser();

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name")
        );
        if (securityService.isAdmin()) {
            return userRepository.findAll(pageable)
                    .map(user -> new UserListResponse(user.getId(), user.getName(), user.getRoom() != null ? user.getRoom().getName() : null));
        }
        return userRepository.findByRoom(currentUser.getRoom(), pageable)
                .map(user -> new UserListResponse(user.getId(), user.getName(), user.getRoom() != null ? user.getRoom().getName() : null));
    }



    public User getUserById(Long id) {
        log.info("Getting user by id {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
    public User deleteUserById(Long id) {
        log.info("Deleting user by id {}", id);
        User user  = userRepository.findById( id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if(taskRepository.existsByUser_Id(id)) {
            throw new IllegalStateException("cannot delete user with tasks");
        }
        userRepository.delete(user);
        return user;
    }

    public User assignToRoom(Long userId, Long roomId) {
        log.info("Assigning user {} to room {}", userId, roomId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        var room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        user.setRoom(room);
        userRepository.save(user);
        return user;
    }
}
