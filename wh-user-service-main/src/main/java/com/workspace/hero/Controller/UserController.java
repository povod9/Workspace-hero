package com.workspace.hero.Controller;


import com.workspace.hero.Entity.LoginRequest;
import com.workspace.hero.Entity.Manager;
import com.workspace.hero.Entity.User;
import com.workspace.hero.Entity.UserDto;
import com.workspace.hero.Service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser(){
        log.info("Called method getAllUser");
        var allUser = service.getAllUser();

        return ResponseEntity.status(200)
                .body(allUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(
            @PathVariable("id") Long id
    )
    {
        log.info("Called method getById");

        var getClientById = service.getById(id);

        return ResponseEntity.status(200)
                .body(getClientById);
    }


    @PostMapping("/register/user")
    public ResponseEntity<Void> createUser
            (
                    @RequestBody @Valid User userToCreate
            ){
        log.info("Called method createUser");

        service.createUser(userToCreate);

        return ResponseEntity.status(201)
                .build();
    }

    @PostMapping("/register/manager")
    public ResponseEntity<Void> createManager
            (
                    @RequestBody @Valid Manager managerToCreate
            ){
        log.info("Called method createManager");


        service.createManager(managerToCreate);
        return ResponseEntity.status(201)
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest loginRequest
    )
    {
        log.info("Called method login");

        String token = service.login(loginRequest);

        return ResponseEntity.ok(Map.of("token", token));

    }

    @PostMapping("/{id}/top-up")
    public ResponseEntity<UserDto> topUp(
            @PathVariable("id") Long id,
            @RequestParam BigDecimal amount
    )
    {
        log.info("Called method topUp");

        return ResponseEntity.ok(service.topUp(id,amount));
    }

    @PostMapping("/me/deduct")
    public ResponseEntity<UserDto> deductBalance(
            @RequestParam("amount") BigDecimal amount
    )
    {
        log.info("Called method deductBalance");

        var deduct = service.deductBalance(amount);
        return ResponseEntity.status(200)
                .body(deduct);
    }

}
