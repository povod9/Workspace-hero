package com.workspace.hero.booking_service.Dto;

import java.math.BigDecimal;

public record UserDto(
        Long id,
        String email,
        String role,
        BigDecimal balance
) {
}
