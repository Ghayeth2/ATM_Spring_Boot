package com.atm.decorator;

import com.atm.business.abstracts.UserRegister;
import com.atm.business.abstracts.UserService;
import com.atm.business.concretes.UserManager;
import com.atm.core.exception.EmailExistsException;
import com.atm.dao.UserDao;
import com.atm.model.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
// Decorator class should not connect directly with DAO Layer
// Thus, instead of calling DAO it should call Services
@Component
public class UserNameExistsDecorator implements UserRegister {
    private final UserDao userDao;
    private final UserService userService;

    @Autowired
    public UserNameExistsDecorator(UserDao userDao, UserService userService) {
        this.userDao = userDao;
        this.userService = userService;
    }

    @Override
    public void save(UserDto userDto) throws EmailExistsException {
        if(userDao.existsByEmail(userDto.getEmail())){
            throw new EmailExistsException("Username already exists!");
        }
        ((UserManager)userService).save(userDto);
    }
}
