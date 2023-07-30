package com.atm.business.abstracts;

import com.atm.model.dtos.UserDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void save(UserDto userDto);
    void update(UserDto userDto, Long id);

    // User cannot delete himself, Admin can
    void delete(Long id);
    List<UserDto> users();
}
