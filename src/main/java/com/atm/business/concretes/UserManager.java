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
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
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
    public void changeRole(Long id){
        User user = userDao.getById(id);
        Optional<Role> role;
        List<Role> roles = new ArrayList<>();
        // When troubling with getting String out of Collections >> Use StringBuilder
        StringBuilder roleName = new StringBuilder();
        user.getRoles().stream().forEach(rol -> roleName.append(rol.getName()));

        if(roleName.toString().contains("ROLE_USER")){
            role = roleService.findByName("ROLE_ADMIN");
            Role role1 = role.get();
            roles.add(role1);
            user.setRoles(roles);
        }else{
            role = roleService.findByName("ROLE_USER");
            Role role1 = role.get();
            roles.add(role1);
            user.setRoles(roles);
        }
        user.setId(id);
        this.userDao.save(user);
    }

    @Override
    public void save(UserDto userDto) {
        User user = (User) converter.dtoToEntity(userDto, new User());
        // Encrypting password
        user.setPassword(passwordEncoder.passwordEncoder().encode(user.getPassword()));
        Optional<Role> roleOptional =  roleService.findByName("ROLE_USER");
        Optional<Role> roleOpt = roleService.findByName("ROLE_ADMIN");

        if(!roleOpt.isPresent() && !roleOptional.isPresent())
            user.setRoles(Arrays.asList(new Role("ROLE_ADMIN")));

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

    // In case of user not found, String with exception will be thrown (REST API)
    @Override
    public void delete(Long id) {
        this.userDao.deleteById(id);
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        Optional<User> user = this.userDao.findById(id);
        if(user.isPresent())
            return (Optional<UserDto>) converter.entityToDto(this.userDao.findById(id), new UserDto());
        throw new UsernameNotFoundException("User is not found");
    }



    public Long getAuthenticatedUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails){
            CustomUserDetailsDto userDetails = (CustomUserDetailsDto) authentication.getPrincipal();
            log.info("user id : "+userDetails.getUser().getId());
            return userDetails.getUser().getId();
        }
        return null;
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
                            .id((Long) row[0])
                            .firstName((String) row[1])
                            .lastName((String) row[2])
                            .email((String) row[3])
                            .name((String) row[4])
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
}
