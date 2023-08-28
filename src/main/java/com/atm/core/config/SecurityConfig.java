package com.atm.core.config;


import com.atm.business.abstracts.UserAccount;
import com.atm.core.bean.DaoAuthenticationProviderBean;
import com.atm.core.bean.LoginSuccessHandlerBean;
import com.atm.core.bean.PasswordEncoderBean;
import com.atm.model.dtos.CustomUserDetailsDto;
import com.atm.model.entities.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.JavaBean;
import java.io.IOException;

@Configuration
@Log4j2
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private PasswordEncoderBean passwordEncoder;
    private DaoAuthenticationProviderBean authProvider;
    private LoginFailureHandlerConfig loginFailureHandler;


    @Autowired
    public SecurityConfig(PasswordEncoderBean passwordEncoder, DaoAuthenticationProviderBean authProvider,
                          LoginFailureHandlerConfig loginFailureHandler) {
        this.passwordEncoder = passwordEncoder;
        this.authProvider = authProvider;
        this.loginFailureHandler = loginFailureHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(authProvider.authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
//                .antMatchers(
//                        "/atm/registration", "/atm/registration?notMatched",
//                        "/atm/registration?success", "" +
//                                "/atm**", "/assets/js/**", "/assets/css/**", "/assets/img/**",
//                "/assets/fonts/**", "/assets/modules/**", "/public/**", "/atm/login"
//                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
//                    .loginPage("/atm/login")
                    .successHandler((request, response, authentication) -> {log.info("Pass");})
                    .failureHandler(loginFailureHandler)
                    .defaultSuccessUrl("/atm")
                .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/atm/login?logout")
                .permitAll();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandlerConfig();
    }
}
