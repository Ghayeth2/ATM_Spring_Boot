package com.atm.services.abstracts;

import com.atm.core.exceptions.EmailExistsException;
import com.atm.models.dtos.UserDto;

import java.util.List;

// UserDetailsServices might have broken ISP & SRP
public interface UserService {
    void update(UserDto userDto, Long id);

    void save(UserDto userDto) throws EmailExistsException;

    // User cannot delete himself, Admin can
    void delete(Long id);
    List<UserDto> users();
}
