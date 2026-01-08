package org.example.dormtaskmanagerapi.application.mapper;

import javax.annotation.processing.Generated;
import org.example.dormtaskmanagerapi.application.Dto.RoomResponses.RoomListResponse;
import org.example.dormtaskmanagerapi.entity.Room;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-08T16:13:51+0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.17 (Microsoft)"
)
@Component
public class RoomMapperImpl implements RoomMapper {

    @Override
    public RoomListResponse toRoomListResponse(Room room) {
        if ( room == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = room.getId();
        name = room.getName();

        RoomListResponse roomListResponse = new RoomListResponse( id, name );

        return roomListResponse;
    }
}
