package com.atm.vallidators;

import com.atm.daos.UserDao;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class UserNameExistsValidator {
    private UserDao userDao;

    @Autowired
    public UserNameExistsValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean validateUserName(String userName) {
        return userDao.existsByEmail(userName);
    }
}
