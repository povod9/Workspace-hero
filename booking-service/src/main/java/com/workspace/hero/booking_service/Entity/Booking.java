package com.workspace.hero.booking_service.Entity;

import com.workspace.hero.booking_service.Entity.enums.BookingStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;

public record Booking(
        @Null
        Long id,
        @Null
        Long userId,
        @NotNull
        Workspace workspace,
        BookingStatus status,
        @FutureOrPresent
        LocalDateTime startTime,
        @Future
        LocalDateTime endTime
) {
}
