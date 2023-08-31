package com.atm.business.abstracts;

import com.atm.model.entities.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(String name);
}
