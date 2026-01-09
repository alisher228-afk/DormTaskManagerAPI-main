package org.example.dormtaskmanagerapi.application.mapper;

import org.example.dormtaskmanagerapi.application.Dto.TaskResponse.TaskResponse;
import org.example.dormtaskmanagerapi.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskResponse toResponse(Task task);
}

