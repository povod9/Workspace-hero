package com.workspace.hero.Entity;


import com.workspace.hero.Entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    String firstName;
    String lastName;
    String email;
    BigDecimal balance;
    Role role;
}
