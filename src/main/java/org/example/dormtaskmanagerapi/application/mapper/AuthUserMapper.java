package org.example.dormtaskmanagerapi.application.mapper;

import org.example.dormtaskmanagerapi.application.Dto.AuthUserResponses.AuthRegisterResponses;
import org.example.dormtaskmanagerapi.entity.AuthUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthUserMapper {
    AuthRegisterResponses toAuthRegisterResponses(AuthUser authUser);
}
