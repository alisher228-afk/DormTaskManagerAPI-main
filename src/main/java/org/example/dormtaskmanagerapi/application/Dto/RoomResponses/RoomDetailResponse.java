package org.example.dormtaskmanagerapi.application.Dto.RoomResponses;

import org.example.dormtaskmanagerapi.application.Dto.UserResponses.UserShortResponse;

import java.util.List;

public record RoomDetailResponse(
        Long id,
        String name,
        List<UserShortResponse> users
) {
}
