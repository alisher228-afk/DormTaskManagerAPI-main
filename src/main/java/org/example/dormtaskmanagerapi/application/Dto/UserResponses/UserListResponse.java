package org.example.dormtaskmanagerapi.application.Dto.UserResponses;

public record UserListResponse(
        Long id,
        String name,
        String room
) {
}
