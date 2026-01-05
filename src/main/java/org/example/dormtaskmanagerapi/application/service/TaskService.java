package org.example.dormtaskmanagerapi.application.service;


import jakarta.persistence.EntityNotFoundException;
import org.example.dormtaskmanagerapi.entity.*;
import org.example.dormtaskmanagerapi.entity.repository.RoomRepository;
import org.example.dormtaskmanagerapi.entity.repository.TaskRepository;
import org.example.dormtaskmanagerapi.entity.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@SuppressWarnings("NullableProblems")
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;


    public TaskService(TaskRepository taskRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with id " + id + " not found"));
    }
    public Page<Task> getAllTasks(int page, int size) {
        Pageable pageable =  PageRequest.of(page, size);
        return taskRepository.findAll(pageable);
    }
    public Task createTask(Task task) {

        User user = userRepository.findById(task.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Room room = roomRepository.findById(task.getRoom().getId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        if (!user.getRoom().getId().equals(room.getId())) {
            throw new IllegalStateException("User does not belong to the specified room");
        }

        if (task.getStatus() == null) {
            task.setStatus(Status.IN_PROGRESS);
        }

        if (task.getType() == null) {
            task.setType(TaskType.CLEAN_ROOM);
        }

        task.setUser(user);
        task.setRoom(room);

        return taskRepository.save(task);
    }


    public Task deleteTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with id " + id + " not found"));
        taskRepository.delete(task);
        return task;
    }

    public Task ApproveTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with id " + id + " not found"));
        if (task.getStatus() == Status.COMPLETED) {
            throw new IllegalStateException("Task is already completed");
        }
        task.setStatus(Status.COMPLETED);
        return taskRepository.save(task);

    }

}
