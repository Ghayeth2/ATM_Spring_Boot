package com.atm.business.concretes;

import com.atm.business.abstracts.UserService;
import com.atm.core.bean.PasswordEncoderBean;
import com.atm.core.utils.converter.DtoEntityConverter;
import com.atm.dao.UserDao;
import com.atm.model.dtos.CustomUserDetailsDto;
import com.atm.model.dtos.UserDto;
import com.atm.model.entities.Role;
import com.atm.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManager implements UserService {

    private UserDao userDao;
    private DtoEntityConverter converter;
    private PasswordEncoderBean passwordEncoder;

    @Autowired
    public UserManager(UserDao userDao, DtoEntityConverter converter, PasswordEncoderBean passwordEncoder) {
        this.userDao = userDao;
        this.converter = converter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(UserDto userDto) {
        User user = (User) converter.dtoToEntity(userDto, new User());
        // Encrypting password
        user.setPassword(passwordEncoder.passwordEncoder().encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public void update(UserDto userDto, Long id) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<UserDto> users() {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password!");
        }
        return new CustomUserDetailsDto(user);

    }

    // Mapping Roles GrantedAuthority
    public List<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
        return roles.stream().map(
                // Changing Roles to SimpleGrantedAuthority Object
                role ->  new SimpleGrantedAuthority(role.getName())
        ).collect(Collectors.toList());
    }
}
