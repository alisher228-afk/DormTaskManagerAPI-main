package org.example.dormtaskmanagerapi.application.mapper;

import org.example.dormtaskmanagerapi.application.Dto.RoomResponses.RoomListResponse;
import org.example.dormtaskmanagerapi.entity.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomListResponse toRoomListResponse(Room room);
}
