package com.workspace.hero.booking_service.Entity;

import com.workspace.hero.booking_service.Entity.enums.WorkSpaceStatus;
import com.workspace.hero.booking_service.Entity.enums.WorkSpaceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record Workspace(
        @Null
        Long id,
        @NotBlank
        String name,
        @NotNull
        WorkSpaceType type,
        WorkSpaceStatus status,
        @NotNull
        @Positive
        BigDecimal pricePerHour
) {
}
