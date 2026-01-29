package com.workspace.hero.booking_service.GlobalExeptionHandler;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalHandler.class);

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> handleException(
            Exception e
    )
    {
        log.error("Handle error" + e);

        var errorDto = new ExceptionDto(
                "Internal server error",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDto);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> handleNotFound(
            EntityNotFoundException e
    )
    {
        log.error("Handle error" + e);

        var errorDto = new ExceptionDto(
                "Entity Not Found Exception",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorDto);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> handleBadRequest(
            IllegalArgumentException e
    )
    {
        log.error("Handle error" + e);

        var errorDto = new ExceptionDto(
                "Bad Request",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }
}
