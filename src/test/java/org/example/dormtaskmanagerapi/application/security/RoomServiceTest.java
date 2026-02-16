package org.example.dormtaskmanagerapi.application.security;

import org.example.dormtaskmanagerapi.application.Dto.RoomResponses.RoomDetailResponse;
import org.example.dormtaskmanagerapi.application.mapper.RoomMapper;
import org.example.dormtaskmanagerapi.application.service.RoomService;
import org.example.dormtaskmanagerapi.entity.Room;
import org.example.dormtaskmanagerapi.entity.User;
import org.example.dormtaskmanagerapi.entity.repository.RoomRepository;
import org.example.dormtaskmanagerapi.entity.repository.TaskRepository;
import org.example.dormtaskmanagerapi.entity.repository.UserRepository;
import org.example.dormtaskmanagerapi.security.SecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {
    @Mock
    private RoomRepository roomRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private RoomMapper roomMapper;

    @Mock
    private SecurityService securityService;

    @InjectMocks
    private RoomService roomService;

    @Test
    void getRoomById_admin_success() {
        // GIVEN
        Long roomId = 1L;

        User admin = new User();
        admin.setId(100L);

        Room room = new Room();
        room.setId(roomId);
        room.setName("Room A");

        when(securityService.getCurrentUser()).thenReturn(admin);
        when(securityService.isAdmin()).thenReturn(true);
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(userRepository.findByRoomId(roomId)).thenReturn(List.of());

        // WHEN
        RoomDetailResponse response = roomService.getRoomById(roomId);

        // THEN
        assertEquals(roomId, response.id());
        assertEquals("Room A", response.name());
    }

    @Test
    void getRoomById_notYourRoom_throwException() {
        // GIVEN
        Long requestedRoomId = 2L;

        Room userRoom = new Room();
        userRoom.setId(1L);

        User user = new User();
        user.setRoom(userRoom);

        when(securityService.getCurrentUser()).thenReturn(user);
        when(securityService.isAdmin()).thenReturn(false);

        // WHEN + THEN
        assertThrows(
                AccessDeniedException.class,
                () -> roomService.getRoomById(requestedRoomId)
        );
    }

    @Test
    void getRoomById_userWithoutRoom_throwException() {
        User user = new User();
        user.setRoom(null);

        when(securityService.getCurrentUser()).thenReturn(user);
        when(securityService.isAdmin()).thenReturn(false);

        assertThrows(
                AccessDeniedException.class,
                () -> roomService.getRoomById(1L)
        );
    }

    @Test
    void deleteRoomById_success() {
        Long roomId = 1L;

        Room room = new Room();
        room.setId(roomId);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(userRepository.existsByRoomId(roomId)).thenReturn(false);
        when(taskRepository.existsByRoom_Id(roomId)).thenReturn(false);

        Room deletedRoom = roomService.deleteRoomById(roomId);

        assertEquals(roomId, deletedRoom.getId());
        verify(roomRepository).delete(room);
    }

    @Test
    void deleteRoomById_withUsers_throwException() {
        Long roomId = 1L;

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(new Room()));
        when(userRepository.existsByRoomId(roomId)).thenReturn(true);

        assertThrows(
                IllegalStateException.class,
                () -> roomService.deleteRoomById(roomId)
        );
    }

}
