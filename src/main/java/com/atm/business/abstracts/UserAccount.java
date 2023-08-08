package com.atm.business.abstracts;

import com.atm.model.entities.User;

public interface UserAccount {
    void increaseFailedAttempts(User user);
    void resetFailedAttempts(String email);
    void lock(User user);
    boolean unlockWhenTimeExpired(User user);
    User findByEmail(String email);
}
