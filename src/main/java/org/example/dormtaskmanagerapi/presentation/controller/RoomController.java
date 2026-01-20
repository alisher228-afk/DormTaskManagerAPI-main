package org.example.dormtaskmanagerapi.presentation.controller;

import org.example.dormtaskmanagerapi.application.Dto.RoomResponses.RoomListResponse;
import org.example.dormtaskmanagerapi.application.Dto.RoomResponses.RoomDetailResponse;
import org.example.dormtaskmanagerapi.entity.Room;
import org.example.dormtaskmanagerapi.application.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/room")
@RestController
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public Room createRoom(@RequestBody Room room) {
        return roomService.createRoom(room);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public Page<RoomListResponse> getAllRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size)
    {
        return roomService.getAllRooms(page, size);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public RoomDetailResponse getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public Room deleteRoomById(@PathVariable Long id) {
        return roomService.deleteRoomById(id);
    }
}
