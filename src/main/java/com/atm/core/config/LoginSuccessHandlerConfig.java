package com.atm.core.config;

import com.atm.business.abstracts.UserAccount;
import com.atm.model.dtos.CustomUserDetailsDto;
import com.atm.model.entities.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Log4j2
public class LoginSuccessHandlerConfig extends SimpleUrlAuthenticationSuccessHandler {
    private UserAccount userAccount;

    @Autowired
    public LoginSuccessHandlerConfig(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomUserDetailsDto userDetails =  (CustomUserDetailsDto) authentication.getPrincipal();
        User user = userDetails.getUser();
        log.info("user email from the success login handler: "+user.getEmail());
        if (user.getFailedAttempts() != 0) {
            userAccount.resetFailedAttempts(user.getEmail());
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
