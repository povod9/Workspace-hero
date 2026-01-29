package com.workspace.hero.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.workspace.hero.Entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;

public record Manager(
        @Null
        Long id,

        @NotBlank
        @JsonProperty("first_name")
        String firstName,

        @NotBlank
        @JsonProperty("last_name")
        String lastName,

        @Email
        @NotBlank
        String email,

        @NotBlank
        String password


) {
}
