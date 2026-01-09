package org.example.dormtaskmanagerapi.application.Dto.AuthUserResponses.login;

public record LoginRequest(
        String username,
        String password
) {
}
