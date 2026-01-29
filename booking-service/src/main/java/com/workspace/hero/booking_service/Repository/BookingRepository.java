package com.workspace.hero.booking_service.Repository;

import com.workspace.hero.booking_service.Entity.BookingEntity;
import com.workspace.hero.booking_service.Entity.enums.BookingStatus;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    boolean existsByWorkspaceEntity_IdAndStartTimeBeforeAndEndTimeAfter(
            Long workspaceId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    List<BookingEntity> findAllByEndTimeBeforeAndStatus(
            LocalDateTime now,
            BookingStatus status
    );

    @Query("SELECT COUNT(b) > 0 FROM BookingEntity b " +
            " WHERE b.workspaceEntity.id = :wsId " +
            " AND b.startTime < :newEnd " +
            " AND b.endTime > :newStart")
    boolean isWorkspaceOccupied(
            @Param("wsId") Long wsId,
            @Param("newStart") LocalDateTime newStart,
            @Param("newEnd") LocalDateTime newEnd
    );
}
