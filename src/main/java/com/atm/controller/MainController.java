package com.atm.controller;

import com.atm.business.abstracts.UserAccount;
import com.atm.model.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/atm")
public class MainController {

    // User side home page
    @GetMapping
    public String index(){
        return "layout/home";
    }
    // Login page
    @GetMapping("/login")
    public String login(){
        return "layout/login";
    }

    // Registration page
    @GetMapping("/registration")
    public String signup(Model model){
        model.addAttribute("user", new UserDto());
        return "layout/signup";
    }

    // Admin side home page
}
