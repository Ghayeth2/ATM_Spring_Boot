package com.atm.business.concretes;

import com.atm.business.abstracts.RoleService;
import com.atm.dao.RoleDao;
import com.atm.model.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleManager implements RoleService {
    private RoleDao roleDao;

    @Autowired
    public RoleManager(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Optional<Role> findByName(String name) {
        // Adding Exception or Optional class
        return this.roleDao.findByName(name);
    }
}
