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
    public void updateFailedAttempts(int failAttempts, String email);

    @Query("select new com.atm.model.dtos.UserRoleDto" +
            "(u.firstName, u.lastName, u.email, r.name) from User u inner join u.roles r")
    List<UserRoleDto> users();
}
