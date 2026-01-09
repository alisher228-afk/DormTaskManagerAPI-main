package org.example.dormtaskmanagerapi.presentation.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.dormtaskmanagerapi.application.Dto.AuthUserResponses.AuthRegisterResponses;
import org.example.dormtaskmanagerapi.application.Dto.AuthUserResponses.RegisterRequest;
import org.example.dormtaskmanagerapi.application.Dto.AuthUserResponses.login.LoginRequest;
import org.example.dormtaskmanagerapi.application.Dto.AuthUserResponses.login.LoginResponse;
import org.example.dormtaskmanagerapi.application.service.AuthUserService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthUserController {
    private final AuthUserService authUserService;

    public AuthUserController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @PostMapping("/register")
    public AuthRegisterResponses register(@RequestBody RegisterRequest request){
        return authUserService.register(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/authUsers")
    public Page<AuthRegisterResponses> getAuthUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size)
    {
        return authUserService.getAuthUsers(page, size);
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authUserService.login(request);
    }

}
