package org.example.dormtaskmanagerapi.application.Dto.TaskResponse;

import org.example.dormtaskmanagerapi.entity.Status;
import org.example.dormtaskmanagerapi.entity.TaskType;

public record TaskResponse(
        Long id,
        String description,
        Status status,
        TaskType type,
        Long userId,
        String userName,
        Long roomId,
        String roomName
) {
}

