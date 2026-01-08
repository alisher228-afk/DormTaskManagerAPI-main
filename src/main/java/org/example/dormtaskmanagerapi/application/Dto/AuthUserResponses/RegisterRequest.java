package org.example.dormtaskmanagerapi.application.Dto.AuthUserResponses;

public record RegisterRequest(
        String username,
        String password,
        String name
) {
}
