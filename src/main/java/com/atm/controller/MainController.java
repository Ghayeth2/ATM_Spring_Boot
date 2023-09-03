package com.atm.controller;

import com.atm.business.abstracts.UserAccount;
import com.atm.business.abstracts.UserService;
import com.atm.model.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/atm")
public class MainController {

    private UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

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

    // Users // localhost:8080/atm/users
    @GetMapping("/users")
    public String users(Model model){
        model.addAttribute("users", this.userService.users());
        return "layout/users";
    }

    // User's Transactions// localhost:8080/atm/transactions

    // Admin side home page
}
