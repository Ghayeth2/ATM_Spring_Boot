package com.atm.controllers.renders;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/atm/auth")
public class AuthController {

    // Login page
    @GetMapping("/login")
    public String login(){
        return "layout/auth/login";
    }
}
