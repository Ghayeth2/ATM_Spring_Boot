package com.atm.core.beans;

import com.atm.services.abstracts.UserService;
import com.atm.services.concretes.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
public class DaoAuthenticationProviderBean {


    private PasswordEncoderBean passwordEncoder;

    private UserService userService;

    @Autowired
    public DaoAuthenticationProviderBean(PasswordEncoderBean passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        // NEED TO SPLIT UserDetailsService TO SEPARATE CLASS
        // loadUserByUsername*=(String username) this should be moved
        auth.setUserDetailsService((UserManager)userService);
        auth.setPasswordEncoder(passwordEncoder.passwordEncoder());
        return auth;
    }
}
