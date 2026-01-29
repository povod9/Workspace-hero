package com.workspace.hero.booking_service.Controller;


import com.workspace.hero.booking_service.Entity.Booking;
import com.workspace.hero.booking_service.Entity.Workspace;
import com.workspace.hero.booking_service.Service.BookingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final Logger log = LoggerFactory.getLogger(BookingController.class);
    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @GetMapping("/workspace")
    public ResponseEntity<List<Workspace>> findAllWorkspace()
    {
        log.info("Called method findAllWorkspace");
        var allWorkspace = service.findAllWorkspace();

        return ResponseEntity.status(200)
                .body(allWorkspace);
    }

    @GetMapping("/workspace/{id}")
    public ResponseEntity<Workspace> findByIdWorkspace(
            @PathVariable("id") @Valid Long id
    )
    {
        log.info("Called method findByIdWorkspace");
        var workspaceById = service.findByIdWorkspace(id);

        return ResponseEntity.status(200)
                .body(workspaceById);

    }

    @PostMapping("/workspace/create")
    public ResponseEntity<Workspace> createWorkspace(
            @RequestBody @Valid Workspace workspaceToCreate
    )
    {
        log.info("Called method createWorkspace");
        var createdWorkspace = service.createWorkspace(workspaceToCreate);

        return ResponseEntity.status(201)
                .body(createdWorkspace);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> findAllBooking()
    {
        log.info("Called method findAllBooking");
        var allBooking = service.findAllBooking();

        return ResponseEntity.status(200)
                .body(allBooking);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> findByIdBooking(
            @PathVariable("id") @Valid Long id
    )
    {
        log.info("Called method findByIdBooking");
        var bookingById = service.findByIdBooking(id);

        return ResponseEntity.status(200)
                .body(bookingById);
    }

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(
            @RequestBody @Valid Booking bookingToCreate,
            @RequestHeader("X-User-Id") Long userId
    )
    {
        log.info("Called method createBooking");
        var createdBooking = service.createBooking(bookingToCreate, userId);

        return ResponseEntity.status(201)
                .body(createdBooking);
    }



}
