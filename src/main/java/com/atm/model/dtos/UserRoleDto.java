package com.atm.model.dtos;

import lombok.*;
import lombok.extern.log4j.Log4j2;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String name;
}
