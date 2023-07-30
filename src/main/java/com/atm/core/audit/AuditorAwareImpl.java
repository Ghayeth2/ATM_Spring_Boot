package com.atm.core.audit;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
@Log4j2
// Auditor config // getting logged in username
public class AuditorAwareImpl implements AuditorAware {
    @Override
    public Optional getCurrentAuditor() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        String username = authentication.getName();
        log.info(username);
        return Optional.ofNullable(username).filter(name -> !name.isEmpty());
    }
}
