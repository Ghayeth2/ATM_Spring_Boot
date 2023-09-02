package com.atm.business.concretes;

import com.atm.business.abstracts.RoleService;
import com.atm.business.abstracts.UserRegister;
import com.atm.business.abstracts.UserService;
import com.atm.core.bean.PasswordEncoderBean;
import com.atm.core.utils.converter.DtoEntityConverter;
import com.atm.dao.UserDao;
import com.atm.model.dtos.CustomUserDetailsDto;
import com.atm.model.dtos.UserDto;
import com.atm.model.dtos.UserRoleDto;
import com.atm.model.entities.Role;
import com.atm.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
public class UserManager implements UserService, UserDetailsService, UserRegister {
    private UserDao userDao;
    private DtoEntityConverter converter;
    private PasswordEncoderBean passwordEncoder;
    private RoleService roleService;

    @Autowired
    public UserManager(UserDao userDao, DtoEntityConverter converter, PasswordEncoderBean passwordEncoder
                    , RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.converter = converter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(UserDto userDto) {
        User user = (User) converter.dtoToEntity(userDto, new User());
        // Encrypting password
        user.setPassword(passwordEncoder.passwordEncoder().encode(user.getPassword()));
        Optional<Role> roleOptional =  roleService.findByName("ROLE_USER");

        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            user.setRoles(Arrays.asList(role));
        }else{
            Role role = Role.builder()
                    .name("ROLE_USER")
                    .build();
            user.setRoles(Arrays.asList(role));
        }
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
    public List<UserRoleDto> users() {
        List<Object[]> results = this.userDao.users();
        // Iterating results > mapping UsersRolesDto row by row
        // converting them into UsersRolesDto object and returning them
        return results.stream()
                .map(row -> {
                    UserRoleDto user = UserRoleDto.
                        builder()
                            .firstName((String) row[0])
                            .lastName((String) row[1])
                            .email((String) row[2])
                            .name((String) row[3])
                        .build();
                    return user;
                })
                .collect(Collectors.toList());
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
