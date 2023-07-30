package com.atm.core.bean;

import com.atm.business.abstracts.UserService;
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
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder.passwordEncoder());
        return auth;
    }
}
