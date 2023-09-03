package com.atm.business.abstracts;

import com.atm.core.exception.EmailExistsException;
import com.atm.model.dtos.UserDto;

public interface UserRegister {
    void save(UserDto userDto) throws EmailExistsException;
}
