package org.example.dormtaskmanagerapi.security;

import org.example.dormtaskmanagerapi.entity.AuthUser;
import org.example.dormtaskmanagerapi.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    public AuthUser getCurrentAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof UserDetailsImpl userDetails)) {
            throw new RuntimeException("Unauthorized");
        }

        return userDetails.getAuthUser();
    }

    public User getCurrentUser() {
        return getCurrentAuthUser().getUser();
    }

    public boolean isAdmin() {
        return getCurrentAuthUser().getRole() == Role.ROLE_ADMIN;
    }
}
