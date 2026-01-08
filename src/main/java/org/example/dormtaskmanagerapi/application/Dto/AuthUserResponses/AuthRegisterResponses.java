package org.example.dormtaskmanagerapi.application.Dto.AuthUserResponses;

import org.example.dormtaskmanagerapi.security.Role;

public record AuthRegisterResponses(
        Long userID,
        String username,
        Role role
) {
}
