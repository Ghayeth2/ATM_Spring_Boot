package com.atm.services.concretes;

import com.atm.core.exceptions.EmailExistsException;
import com.atm.services.abstracts.UserService;
import com.atm.core.beans.PasswordEncoderBean;
import com.atm.core.utils.converter.DtoEntityConverter;
import com.atm.daos.UserDao;
import com.atm.models.dtos.CustomUserDetailsDto;
import com.atm.models.dtos.UserDto;
import com.atm.models.entities.Role;
import com.atm.models.entities.User;
import com.atm.vallidators.UserNameExistsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/* ISP > extending the UserDetailsService interface
 has broken the ISP not all concrete classes might
 implement that interface, so we do not extend it
 instead we implement it in this class
 it is already an interface, so no need to add our
 own one.
 */
/*
    In addition, now i broke the UserService interface
    to add another one with only save() method, we have
    another class that won't implement all methods in our
    service interface. just the one.
 */
@Service
public class UserManager implements UserService, UserDetailsService {
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
    public void save(UserDto userDto) throws EmailExistsException {
        // Checking user existence
        if (new UserNameExistsValidator().validateUserName(
                userDto.getEmail()
        ))
            throw new EmailExistsException("Email already exists, try logging in!");
        // Saving user in case if not existed
        User user = (User) converter.dtoToEntity(userDto, new User());
        // Encrypting password
        user.setPassword(passwordEncoder.passwordEncoder().encode(user.getPassword()));
        // just once, or manually from database
        user.setRoles(List.of(new Role("ROLE_ADMIN")));
        // setting user's slug
        user.setSlug(user.getEmail());
        user.setAccountNonLocked(1);
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
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password!");
        }
        return new CustomUserDetailsDto(user);

    }

    // Mapping Roles GrantedAuthority // SRP Violation
    public List<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
        return roles.stream().map(
                // Changing Roles to SimpleGrantedAuthority Object
                role ->  new SimpleGrantedAuthority(role.getName())
        ).collect(Collectors.toList());
    }
}
