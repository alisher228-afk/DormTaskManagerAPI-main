package org.example.dormtaskmanagerapi.application.mapper;

import javax.annotation.processing.Generated;
import org.example.dormtaskmanagerapi.application.Dto.AuthUserResponses.AuthRegisterResponses;
import org.example.dormtaskmanagerapi.entity.AuthUser;
import org.example.dormtaskmanagerapi.security.Role;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-08T16:13:51+0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.17 (Microsoft)"
)
@Component
public class AuthUserMapperImpl implements AuthUserMapper {

    @Override
    public AuthRegisterResponses toAuthRegisterResponses(AuthUser authUser) {
        if ( authUser == null ) {
            return null;
        }

        String username = null;
        Role role = null;

        username = authUser.getUsername();
        role = authUser.getRole();

        Long userID = null;

        AuthRegisterResponses authRegisterResponses = new AuthRegisterResponses( userID, username, role );

        return authRegisterResponses;
    }
}
