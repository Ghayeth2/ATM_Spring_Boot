package com.atm.core.config;


import com.atm.core.bean.DaoAuthenticationProviderBean;
import com.atm.core.bean.PasswordEncoderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoderBean passwordEncoder;

    @Autowired private DaoAuthenticationProviderBean authProvider;

    @Autowired
    private LoginFailureHandlerConfig loginFailureHandler;
    @Autowired
    private LoginSuccessHandlerConfig loginSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(authProvider.authenticationProvider());
    }

    // To control the ROLE BASED Logging in, we add .hasRole() as what we want to urls down here
    // FURTHER MORE, SEE MY TELEGRAM OR STACKOVERFLOW
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers(
                        "/atm/registration", "/atm/registration?notMatched",
                        "/atm/registration?success", "" +
                                "/atm**", "/assets/js/**", "/assets/css/**", "/assets/img/**",
                "/assets/fonts/**", "/assets/modules/**", "/public/**", "/atm/login"
                ).permitAll()
//                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/atm/login")
                    .permitAll()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .permitAll()
                    .defaultSuccessUrl("/atm")
                .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/atm/login?logout")
                .permitAll();
    }
}
