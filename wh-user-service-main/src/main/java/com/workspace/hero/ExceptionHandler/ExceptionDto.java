package com.workspace.hero.ExceptionHandler;

import java.time.LocalDateTime;

public record ExceptionDto(
        String message,
        String errorMessage,
        LocalDateTime time
) {
}
