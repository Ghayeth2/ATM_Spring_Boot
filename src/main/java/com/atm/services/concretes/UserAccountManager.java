package com.atm.services.concretes;

import com.atm.services.abstracts.UserAccount;
import com.atm.daos.UserDao;
import com.atm.models.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class UserAccountManager implements UserAccount {
    private UserDao userDao;
    public static final int MAX_FAILED_ATTEMPTS = 3;
    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours

    @Autowired
    public UserAccountManager(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void increaseFailedAttempts(User user) {
        int newFailedA = user.getFailedAttempts()+1;
        userDao.updateFailedAttempts(newFailedA, user.getEmail());
    }

    @Override
    public void resetFailedAttempts(String email) {
        userDao.updateFailedAttempts(0, email);
    }

    @Override
    public void lock(User user) {
        user.setAccountNonLocked(0);
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        user.setLockTime(new Date());
        userDao.save(user);
    }

    @Override
    public boolean unlockWhenTimeExpired(User user) {
        long lockTimeInMillis = user.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();
        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            user.setAccountNonLocked(1);
            user.setLockTime(null);
            user.setFailedAttempts(0);
            userDao.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
