package org.example.dormtaskmanagerapi.exception;

import java.time.LocalDateTime;

public record ExMessage(
        String message,
        String detailedMessage,
        LocalDateTime errorTime
)
{}
