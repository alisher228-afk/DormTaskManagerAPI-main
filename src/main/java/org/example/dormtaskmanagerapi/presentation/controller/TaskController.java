package org.example.dormtaskmanagerapi.presentation.controller;

import org.example.dormtaskmanagerapi.entity.Task;
import org.example.dormtaskmanagerapi.application.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("NullableProblems")
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public Page<Task> getAllTasks(@RequestParam int page, @RequestParam int size) {
        return taskService.getAllTasks(page, size);
    }
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }
    @PostMapping("/create")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }
    @DeleteMapping("/delete/{id}")
    public Task deleteTaskById(@PathVariable Long id) {
        return taskService.deleteTaskById(id);
    }
    @PutMapping("/complete/{id}")
    public Task ApproveTask(@PathVariable Long id) {
        return taskService.ApproveTask(id);
    }
}
