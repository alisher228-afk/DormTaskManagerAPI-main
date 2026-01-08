package org.example.dormtaskmanagerapi.application.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.dormtaskmanagerapi.application.Dto.UserResponses.UserListResponse;
import org.example.dormtaskmanagerapi.entity.AuthUser;
import org.example.dormtaskmanagerapi.entity.User;
import org.example.dormtaskmanagerapi.entity.repository.RoomRepository;
import org.example.dormtaskmanagerapi.entity.repository.TaskRepository;
import org.example.dormtaskmanagerapi.entity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@SuppressWarnings("NullableProblems")
@Service
public class UserService {
    public static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final RoomRepository roomRepository;

    public UserService(UserRepository userRepository, TaskRepository taskRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.roomRepository = roomRepository;
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
        log.info("Getting users");
        Pageable pageable =  PageRequest.of(page, size , Sort.by(Sort.Direction.ASC, "name"));
        return userRepository.findAll(pageable)
                .map(user -> new UserListResponse(user.getId(),user.getName(),user.getRoom()));
    }
    public User getUserById(Long id) {
        log.info("Getting user by id {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return user;
    }
    public User deleteUserById(Long id) {
        log.info("Deleting user by id {}", id);
        User user  = userRepository.findById( id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if(taskRepository.existsByUserId(id)) {
            throw new IllegalStateException("cannot delete user with tasks");
        }
        userRepository.delete(user);
        return user;
    }
}
