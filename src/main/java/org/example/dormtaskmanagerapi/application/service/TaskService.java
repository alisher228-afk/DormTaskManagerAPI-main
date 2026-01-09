package org.example.dormtaskmanagerapi.application.service;


import jakarta.persistence.EntityNotFoundException;
import org.example.dormtaskmanagerapi.entity.*;
import org.example.dormtaskmanagerapi.entity.repository.RoomRepository;
import org.example.dormtaskmanagerapi.entity.repository.TaskRepository;
import org.example.dormtaskmanagerapi.entity.repository.UserRepository;
import org.example.dormtaskmanagerapi.security.SecurityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@SuppressWarnings("NullableProblems")
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final SecurityService securityService;


    public TaskService(TaskRepository taskRepository, UserRepository userRepository, RoomRepository roomRepository, SecurityService securityService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.securityService = securityService;
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with id " + id + " not found"));
    }
    public Page<Task> getAllTasks(int page, int size) {
        User currentUser = securityService.getCurrentUser();

        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findByUser(currentUser, pageable);
    }



    public Task createTask(Task task) {

        User currentUser = securityService.getCurrentUser();

        Room room = roomRepository.findById(task.getRoom().getId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        if (!currentUser.getRoom().getId().equals(room.getId())) {
            throw new AccessDeniedException("You cannot create task for another room");
        }

        task.setUser(currentUser);
        task.setRoom(room);

        if (task.getStatus() == null) {
            task.setStatus(Status.IN_PROGRESS);
        }

        if (task.getType() == null) {
            task.setType(TaskType.CLEAN_ROOM);
        }

        return taskRepository.save(task);
    }



    public Task deleteTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with id " + id + " not found"));
        taskRepository.delete(task);
        return task;
    }

public Task approveTask(Long taskId) {
    Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new EntityNotFoundException("Task not found"));

    User currentUser = securityService.getCurrentUser();

    if (!task.getUser().getId().equals(currentUser.getId())) {
        throw new AccessDeniedException("This is not your task");
    }

    task.setStatus(Status.COMPLETED);
    return taskRepository.save(task);
}


}
