package org.example.dormtaskmanagerapi.presentation.controller;

import org.example.dormtaskmanagerapi.application.Dto.AuthUserResponses.AuthRegisterResponses;
import org.example.dormtaskmanagerapi.application.Dto.AuthUserResponses.RegisterRequest;
import org.example.dormtaskmanagerapi.application.service.AuthUserService;
import org.example.dormtaskmanagerapi.entity.AuthUser;
import org.springframework.data.domain.Page;
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


    @GetMapping("/authUsers")
    public Page<AuthRegisterResponses> getAuthUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size)
    {
        return authUserService.getAuthUsers(page, size);
    }
}
