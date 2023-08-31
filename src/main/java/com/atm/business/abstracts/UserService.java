package com.atm.business.abstracts;

import com.atm.model.dtos.UserDto;
import com.atm.model.dtos.UserRoleDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

// UserDetailsServices might have broken ISP & SRP
public interface UserService {
    void update(UserDto userDto, Long id);

    // User cannot delete himself, Admin can
    void delete(Long id);
    List<UserRoleDto> users();
}
