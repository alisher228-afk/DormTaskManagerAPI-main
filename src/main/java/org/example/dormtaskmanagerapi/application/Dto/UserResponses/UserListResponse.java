package org.example.dormtaskmanagerapi.application.Dto.UserResponses;

public record UserListResponse(
        Long id,
        String name,
        org.example.dormtaskmanagerapi.entity.Room room
) {
}
