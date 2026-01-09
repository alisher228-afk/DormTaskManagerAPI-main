package org.example.dormtaskmanagerapi.presentation.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.dormtaskmanagerapi.application.Dto.TaskResponse.TaskResponse;
import org.example.dormtaskmanagerapi.application.mapper.TaskMapper;
import org.example.dormtaskmanagerapi.entity.Task;
import org.example.dormtaskmanagerapi.application.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("NullableProblems")
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public Page<Task> getAllTasks(@RequestParam int page, @RequestParam int size) {
        return taskService.getAllTasks(page, size);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public TaskResponse createTask(@RequestBody Task task) {
        return taskMapper.toResponse(taskService.createTask(task));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public Task deleteTaskById(@PathVariable Long id) {
        return taskService.deleteTaskById(id);
    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/complete/{id}")
    public Task approveTask(@PathVariable Long id) {
        return taskService.approveTask(id);
    }
}
