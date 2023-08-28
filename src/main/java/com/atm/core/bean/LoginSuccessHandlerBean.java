package com.atm.core.bean;

import com.atm.core.config.LoginSuccessHandlerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginSuccessHandlerBean {
    @Bean
    public LoginSuccessHandlerConfig getLoginSuccess(){
        return new LoginSuccessHandlerConfig();
    }
}
