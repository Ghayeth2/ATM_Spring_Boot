package com.atm.daos;

import com.atm.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findBySlug(String slug);
    boolean existsByEmail(String email);

    @Query("UPDATE User u SET u.failedAttempts = ?1 WHERE u.email = ?2")
    @Modifying
    public void updateFailedAttempts(int failAttempts, String email);
}
