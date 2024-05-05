package com.atm.core.beans;

import com.atm.core.configs.LoginSuccessHandlerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginSuccessHandlerBean {
    @Bean
    public LoginSuccessHandlerConfig getLoginSuccess(){
        return new LoginSuccessHandlerConfig();
    }
}
