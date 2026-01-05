package org.example.dormtaskmanagerapi.presentation.controller;

import org.example.dormtaskmanagerapi.application.Dto.UserResponses.UserListResponse;
import org.example.dormtaskmanagerapi.entity.User;
import org.example.dormtaskmanagerapi.application.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("NullableProblems")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userService.createUsers(user);
    }
    @GetMapping
    public Page<UserListResponse> getUsers(@RequestParam int page, @RequestParam int size) {
        return userService.getUsers(page, size);
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    @DeleteMapping("/delete/{id}")
    public User deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }

}
