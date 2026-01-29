package com.workspace.hero.booking_service.GlobalExeptionHandler;

import java.time.LocalDateTime;

public record ExceptionDto(
        String message,
        String errorMessage,
        LocalDateTime time
) {
}
