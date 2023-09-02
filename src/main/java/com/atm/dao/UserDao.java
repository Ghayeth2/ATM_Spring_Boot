package com.atm.dao;

import com.atm.model.dtos.UserRoleDto;
import com.atm.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("UPDATE User u SET u.failedAttempts = ?1 WHERE u.email = ?2")
    @Modifying
    void updateFailedAttempts(int failAttempts, String email);

    @Query(value = "SELECT u.first_name, u.last_name, u.email, r.name\n" +
            "FROM atm.users u\n" +
            "INNER JOIN atm.users_roles ur ON u.id = ur.user_id\n" +
            "INNER JOIN atm.roles r ON ur.role_id = r.id", nativeQuery = true)
    List<Object[] > users();
}
