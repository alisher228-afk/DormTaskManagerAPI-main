package org.example.dormtaskmanagerapi.application.service;

import jakarta.persistence.EntityExistsException;
import org.example.dormtaskmanagerapi.application.Dto.AuthUserResponses.AuthRegisterResponses;
import org.example.dormtaskmanagerapi.application.Dto.AuthUserResponses.RegisterRequest;
import org.example.dormtaskmanagerapi.application.Dto.AuthUserResponses.login.LoginRequest;
import org.example.dormtaskmanagerapi.application.Dto.AuthUserResponses.login.LoginResponse;
import org.example.dormtaskmanagerapi.application.mapper.AuthUserMapper;
import org.example.dormtaskmanagerapi.entity.AuthUser;
import org.example.dormtaskmanagerapi.entity.User;
import org.example.dormtaskmanagerapi.entity.repository.AuthUserRepository;
import org.example.dormtaskmanagerapi.security.Role;
import org.example.dormtaskmanagerapi.security.jwt.JwtService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService {
    private final AuthUserRepository authUserRepository;
    private final AuthUserMapper authUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthUserService(AuthUserRepository authUserRepository, AuthUserMapper authUserMapper, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authUserRepository = authUserRepository;
        this.authUserMapper = authUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthRegisterResponses register(RegisterRequest request) {

        if (authUserRepository.findByUsername(request.username()).isPresent()) {
            throw new EntityExistsException("Username already exists");
        }

        AuthUser authUser = new AuthUser();
        authUser.setUsername(request.username());
        authUser.setPassword(passwordEncoder.encode(request.password()));
        authUser.setRole(Role.ROLE_USER);

        User user = new User();
        user.setName(request.name());

        authUser.setUser(user);
        user.setAuthUser(authUser);

        authUserRepository.save(authUser);

        return authUserMapper.toAuthRegisterResponses(authUser);
    }


    public Page<AuthRegisterResponses> getAuthUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return authUserRepository.findAll(pageable)
                .map(authUserMapper::toAuthRegisterResponses);
    }

    public LoginResponse login(LoginRequest request) {

        AuthUser authUser = authUserRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), authUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(authUser.getUsername());
        return new LoginResponse(token);
    }


}
