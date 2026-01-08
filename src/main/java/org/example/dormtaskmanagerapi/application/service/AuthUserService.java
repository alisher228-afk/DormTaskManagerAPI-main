package org.example.dormtaskmanagerapi.application.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.example.dormtaskmanagerapi.application.Dto.AuthUserResponses.AuthRegisterResponses;
import org.example.dormtaskmanagerapi.application.Dto.AuthUserResponses.RegisterRequest;
import org.example.dormtaskmanagerapi.application.mapper.AuthUserMapper;
import org.example.dormtaskmanagerapi.entity.AuthUser;
import org.example.dormtaskmanagerapi.entity.Room;
import org.example.dormtaskmanagerapi.entity.User;
import org.example.dormtaskmanagerapi.entity.repository.AuthUserRepository;
import org.example.dormtaskmanagerapi.entity.repository.RoomRepository;
import org.example.dormtaskmanagerapi.security.Role;
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
    private final RoomRepository roomRepository;

    public AuthUserService(AuthUserRepository authUserRepository, AuthUserMapper authUserMapper, PasswordEncoder passwordEncoder, RoomRepository roomRepository) {
        this.authUserRepository = authUserRepository;
        this.authUserMapper = authUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.roomRepository = roomRepository;
    }

    public AuthRegisterResponses register(RegisterRequest request) {

        if (authUserRepository.findByUsername(request.username()).isPresent()) {
            throw new EntityExistsException("Username already exists");
        }

        AuthUser authUser = new AuthUser();
        authUser.setUsername(request.username());
        authUser.setPassword(passwordEncoder.encode(request.password()));
        authUser.setRole(Role.USER);

        User user = new User();
        user.setName(request.name());

        authUser.setUser(user);
        user.setAuthUser(authUser);

        AuthUser saved = authUserRepository.save(authUser);
        return authUserMapper.toAuthRegisterResponses(saved);
    }

    public Page<AuthRegisterResponses> getAuthUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return authUserRepository.findAll(pageable)
                .map(authUserMapper::toAuthRegisterResponses);
    }
}
