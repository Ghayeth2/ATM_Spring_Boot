package com.atm.business.abstracts;

import com.atm.core.exception.EmailExistsException;
import com.atm.model.dtos.UserDto;
import com.atm.model.dtos.UserRoleDto;
import com.atm.model.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

// UserDetailsServices might have broken ISP & SRP
public interface UserService {
    void update(UserDto userDto, Long id);
    void changeRole(Long id);
    // User cannot delete himself, Admin can
    void delete(Long id);

    Optional<UserDto> findById(Long id);
    List<UserRoleDto> users();
}
