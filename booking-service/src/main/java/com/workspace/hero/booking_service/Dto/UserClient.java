package com.workspace.hero.booking_service.Dto;

import com.workspace.hero.booking_service.Feign.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "user-service", url = "${user-service.url}")
public interface UserClient {

    @GetMapping("/users/{id}")
    public UserDto getUserById(
            @PathVariable("id") Long id
    );

    @PostMapping("/users/{id}/top-up")
    public UserDto topUpBalance(
            @PathVariable("id") Long id,
            @RequestParam("amount") BigDecimal amount
            );

    @PostMapping("/users/{id}/deduct")
    public UserDto deductBalance(
            @PathVariable("id") Long id,
            @RequestParam("amount") BigDecimal amount
    );
}
