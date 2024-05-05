package com.atm.controllers.renders;

import com.atm.models.dtos.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/atm")
public class PublicController {


    // Registration page
    @GetMapping("/registration")
    public String signup(Model model){
        model.addAttribute("user", new UserDto());
        return "layout/auth/signup";
    }
}
