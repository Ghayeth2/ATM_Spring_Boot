package com.atm.model.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Log4j2 @Builder
public class UserDto {
    @NotEmpty(message = "This field is required!")
    private String firstName;
    @NotEmpty(message = "This field is required!")
    private String lastName;
    @Email(message = "Enter email formatted text")
    @NotEmpty(message = "This field is required!")
    private String email;
//    @NotEmpty(message = "This field is required!")
    @Size(min = 8, max = 40, message = "Password should be in between 8 and 40 characters")
    private String password;
    private String password2;

}
