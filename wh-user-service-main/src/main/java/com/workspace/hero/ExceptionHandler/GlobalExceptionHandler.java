package com.workspace.hero.ExceptionHandler;


import jakarta.persistence.EntityNotFoundException;
import jdk.jfr.Experimental;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> handleException(
            Exception e
    )
    {
        log.error("Handle exception" + e);

        var errorDto = new ExceptionDto(
                "Internal server error",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDto);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> handleEntityNotFound(
            EntityNotFoundException e
    ){
        log.error("Handle EntityNotFound" + e);

        var errorDto = new ExceptionDto(
                "Entity not found",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorDto);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> handleIllegalArgumentException(
            IllegalArgumentException e
    ){
        log.error("Handle BadRequest" + e);

        var errorDto = new ExceptionDto(
                "Bad request",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDto> handleForbidden(
            AccessDeniedException e
    )
    {
        log.error("Handle error " + e);

        ExceptionDto errorDto = new ExceptionDto(
                "Forbidden",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(errorDto);
    }

}
