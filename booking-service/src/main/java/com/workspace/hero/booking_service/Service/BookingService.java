package com.workspace.hero.booking_service.Service;

import com.workspace.hero.booking_service.Controller.BookingController;
import com.workspace.hero.booking_service.Dto.UserClient;
import com.workspace.hero.booking_service.Dto.UserDto;
import com.workspace.hero.booking_service.Entity.Booking;
import com.workspace.hero.booking_service.Entity.BookingEntity;
import com.workspace.hero.booking_service.Entity.Workspace;
import com.workspace.hero.booking_service.Entity.WorkspaceEntity;
import com.workspace.hero.booking_service.Entity.enums.BookingStatus;
import com.workspace.hero.booking_service.Entity.enums.WorkSpaceStatus;
import com.workspace.hero.booking_service.Repository.BookingRepository;
import com.workspace.hero.booking_service.Repository.WorkspaceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Service
public class BookingService {

    private final Logger log = LoggerFactory.getLogger(BookingController.class);

    private final RedisTemplate<String, Object> redisTemplate;
    private final WorkspaceRepository workspaceRepositoryrepository;
    private final BookingRepository bookingRepository;
    private final UserClient userClient;

    public BookingService(RedisTemplate<String, Object> redisTemplate, WorkspaceRepository workspaceRepositoryrepository, BookingRepository bookingRepository, UserClient userClient) {
        this.redisTemplate = redisTemplate;
        this.workspaceRepositoryrepository = workspaceRepositoryrepository;
        this.bookingRepository = bookingRepository;
        this.userClient = userClient;
    }

    public List<Workspace> findAllWorkspace() {

        List<WorkspaceEntity> workspaceEntity = workspaceRepositoryrepository.findAll();

        return workspaceEntity.stream()
                .map(this::toDomainWorkspace)
                .toList();
    }

    public Workspace findByIdWorkspace(
            Long id
    )
    {
        WorkspaceEntity workspaceEntity = workspaceRepositoryrepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot found workspace by id=" + id));

        return toDomainWorkspace(workspaceEntity);
    }

    public Workspace createWorkspace(
            Workspace workspaceToCreate
    )
    {
        var createdEntity = new WorkspaceEntity(
                workspaceToCreate.id(),
                workspaceToCreate.name(),
                workspaceToCreate.type(),
                WorkSpaceStatus.FREE,
                workspaceToCreate.pricePerHour()
        );

        WorkspaceEntity savedEntity = workspaceRepositoryrepository.save(createdEntity);
        log.info("The workspace was successfully saved");
        return toDomainWorkspace(savedEntity);
    }

    public List<Booking> findAllBooking() {

        List<BookingEntity> bookingEntities = bookingRepository.findAll();

        return bookingEntities.stream()
                .map(this::toDomainBooking)
                .toList();
    }

    public Booking findByIdBooking(
            Long id
    )
    {
        BookingEntity bookingEntity = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot found booking by id=" + id));

        return toDomainBooking(bookingEntity);
    }

    @Validated
    @Transactional
    public Booking createBooking(
            Booking bookingToCreate,
            Long userId
    )
    {
        log.info("Starting booking process for user: {}", userId);

        String lockKey = "lock:workspace:" + bookingToCreate.workspace().id();

        Boolean acquired = redisTemplate.opsForValue()
                .setIfAbsent(lockKey, "LOCKED", Duration.ofSeconds(5));

        if (Boolean.FALSE.equals(acquired)){
            throw new IllegalStateException("This table is currently being booked by another user, please try again in a second.");
        }

        try {
            WorkspaceEntity workspaceEntity = workspaceRepositoryrepository.findByNameAndType(
                            bookingToCreate.workspace().name(),
                            bookingToCreate.workspace().type()
                    )
                    .orElseThrow(() -> new EntityNotFoundException("Cannot found workspace by id=" + bookingToCreate.workspace().id()));

            boolean isOccupied = bookingRepository.isWorkspaceOccupied(
                    workspaceEntity.getId(),
                    bookingToCreate.startTime(),
                    bookingToCreate.endTime()
            );


            if (isOccupied) {
                throw new IllegalArgumentException("Sorry, this time is already taken");
            }

            workspaceEntity.setStatus(WorkSpaceStatus.BUSY);
            workspaceRepositoryrepository.save(workspaceEntity);


            UserDto user = userClient.getUserById(userId);

            long hours = java.time.Duration.between(bookingToCreate.startTime(), bookingToCreate.endTime()).toHours();
            if (hours <= 0) hours = 1;
            BigDecimal totalPrice = workspaceEntity.getPricePerHour().multiply(BigDecimal.valueOf(hours));

            log.info("User balance checked successfully");

            if (user.balance().compareTo(totalPrice) < 0) {
                throw new IllegalArgumentException("Insufficient funds, your balance: " + user.balance());
            }

            var createdEntity = new BookingEntity(
                    null,
                    userId,
                    workspaceEntity,
                    BookingStatus.ACTIVE,
                    bookingToCreate.startTime(),
                    bookingToCreate.endTime()
            );

            bookingRepository.save(createdEntity);
            userClient.deductBalance(userId, totalPrice);

            return toDomainBooking(createdEntity);
        }finally {
            redisTemplate.delete(lockKey);
        }
    }

    private Booking toDomainBooking(
            BookingEntity booking
    )
    {
        var newBooking = new Booking(
                booking.getId(),
                booking.getUserId(),
                toDomainWorkspace(booking.getWorkspaceEntity()),
                booking.getStatus(),
                booking.getStartTime(),
                booking.getEndTime()
        );

        return newBooking;
    }

    public Workspace toDomainWorkspace (
            WorkspaceEntity workspace
    )
    {
        var newWorkspace = new Workspace(
                workspace.getId(),
                workspace.getName(),
                workspace.getType(),
                workspace.getStatus(),
                workspace.getPricePerHour()
        );

        return newWorkspace;
    }
}
