package com.workspace.hero.booking_service.Service;

import com.workspace.hero.booking_service.Dto.UserClient;
import com.workspace.hero.booking_service.Dto.UserDto;
import com.workspace.hero.booking_service.Entity.Booking;
import com.workspace.hero.booking_service.Entity.Workspace;
import com.workspace.hero.booking_service.Entity.WorkspaceEntity;
import com.workspace.hero.booking_service.Entity.enums.BookingStatus;
import com.workspace.hero.booking_service.Entity.enums.WorkSpaceType;
import com.workspace.hero.booking_service.Repository.BookingRepository;
import com.workspace.hero.booking_service.Repository.WorkspaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserClient userClient;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;



    @Test
    void createBookingSuccessfully() {

        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);

        when(valueOperations.setIfAbsent(anyString(),any(),any()))
                .thenReturn(true);

        when(userClient.getUserById(1L)).
                thenReturn(new UserDto(1L,
                "asfwAf@gmail.com",
                "MANAGER",
                new BigDecimal("10000")));

        WorkspaceEntity workspaceEntity =  new WorkspaceEntity(
                1L,
                "Desk A1",
                WorkSpaceType.DESK,
                BigDecimal.TEN
        );

        Workspace workspaceEntityFake =  new Workspace(
                1L,
                "Desk A1",
                WorkSpaceType.DESK,
                BigDecimal.TEN
        );

        doReturn(Optional.of(workspaceEntity)).
                when(workspaceRepository)
                .findByNameAndType(anyString(), any());

        bookingService.createBooking(new Booking(
                null,
                null,
                workspaceEntityFake,
                BookingStatus.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15)
        ), 1L);

        verify(bookingRepository, times(1))
                .save(any());

    }

    @Test
    void createBookingNegative(){

        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);

        when(valueOperations.setIfAbsent(anyString(),any(),any()))
                .thenReturn(true);

        WorkspaceEntity workspaceEntity = new WorkspaceEntity(
                1L,
                "Desk A1",
                WorkSpaceType.DESK,
                BigDecimal.TEN
        );

        Workspace workspaceDto = new Workspace(
                1L,
                "Desk A1",
                WorkSpaceType.DESK,
                BigDecimal.TEN
        );

        when(userClient.getUserById(1L))
                .thenReturn(new UserDto(
                1L,
                "safawf@gmail.com",
                "USER",
                new BigDecimal("0")
        ));

        doReturn(Optional.of(workspaceEntity))
                .when(workspaceRepository)
                .findByNameAndType(anyString(),any());

        bookingService.createBooking(new Booking(
                null,
                null,
                workspaceDto,
                BookingStatus.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15)
        ),1L);

        verify(bookingRepository, never())
                .save(any());
    }

    @Test
    void createWorkspaceSuccessfully(){
        when(workspaceRepository.save(any()))
                .thenReturn(new WorkspaceEntity(
                null,
                "Room A-1",
                WorkSpaceType.ROOM,
                new BigDecimal("10")
        ));

        bookingService.createWorkspace(new Workspace(
                null,
                "Room A-1",
                WorkSpaceType.ROOM,
                new BigDecimal("10")
        ));

        verify(workspaceRepository, times(1))
                .save(any());
    }

    @Test
    void createBookingBoundaryTesting(){

        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);

        when(valueOperations.setIfAbsent(anyString(),any(), any()))
                .thenReturn(true);

        Workspace workspaceEntity = new Workspace(
                null,
                "Desk A-1",
                WorkSpaceType.DESK,
                new BigDecimal("10")
        );

        Workspace workspaceDto = new Workspace(
                null,
                "Desk A-1",
                WorkSpaceType.DESK,
                new BigDecimal("10")
        );

        doReturn(Optional.of(workspaceEntity))
                .when(workspaceRepository)
                .findByNameAndType(anyString(),any());

        bookingService.createBooking(new Booking(
                null,
                null,
                workspaceDto,
                BookingStatus.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now().minusMinutes(15)
        ), 1L);

        verify(bookingRepository, never())
                .save(any());
    }

    @Test
    void createBookingRedisTemplateTesting(){

        when(redisTemplate.opsForValue()).
                thenReturn(valueOperations);

        when(valueOperations.setIfAbsent(anyString(),any(),any()))
                .thenReturn(false);

        WorkspaceEntity workspaceEntity = new WorkspaceEntity(
                null,
                "Desk A-1",
                WorkSpaceType.DESK,
                new BigDecimal("10")
        );

        Workspace workspaceDto = new Workspace(
                null,
                "Desk A-1",
                WorkSpaceType.DESK,
                new BigDecimal("10")
        );

        doReturn(Optional.of(workspaceEntity))
                .when(workspaceRepository)
                .findByNameAndType(anyString(),any());

        bookingService.createBooking(new Booking(
                null,
                null,
                workspaceDto,
                BookingStatus.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15)
        ), 1L);

        verify(bookingRepository, never())
                .save(any());
    }
}