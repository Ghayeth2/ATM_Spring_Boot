package com.atm.core.configs;

import com.atm.services.abstracts.UserAccount;
import com.atm.models.dtos.CustomUserDetailsDto;
import com.atm.models.entities.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class LoginSuccessHandlerConfig implements AuthenticationSuccessHandler{
    @Autowired
    private UserAccount userAccount;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomUserDetailsDto userDetails =  (CustomUserDetailsDto) authentication.getPrincipal();
        User user = userDetails.getUser();
        log.info("user email from the success login handler: "+user.getEmail());
        if (user.getFailedAttempts() > 0) {
            userAccount.resetFailedAttempts(user.getEmail());
        }
        new DefaultRedirectStrategy().sendRedirect(request, response, "/atm");
//        super.onAuthenticationSuccess(request, response, authentication);
    }
}
