package com.atm.services.abstracts;

import com.atm.models.entities.User;

public interface UserAccount {
    void increaseFailedAttempts(User user);
    void resetFailedAttempts(String email);
    void lock(User user);
    boolean unlockWhenTimeExpired(User user);
    User findByEmail(String email);
//    String resetPassword(User user);
}
