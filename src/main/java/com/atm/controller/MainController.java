package com.atm.controller;

import com.atm.business.abstracts.UserAccount;
import com.atm.business.abstracts.UserService;
import com.atm.business.concretes.UserManager;
import com.atm.model.dtos.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Log4j2
@RequestMapping("/atm")
public class MainController {

    private UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    // User side home page
    @GetMapping
    public ModelAndView index(Model model){
        model.addAttribute("userId", ((UserManager)this.userService).getAuthenticatedUserId());
        ModelAndView modelAndView = new ModelAndView("fragments/navbarFragment");
        modelAndView.setViewName("layout/home");
        return modelAndView;
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
    public ModelAndView users(Model model){
        model.addAttribute("userId", ((UserManager)this.userService).getAuthenticatedUserId());
        ModelAndView modelAndView = new ModelAndView("fragments/navbarFragment");
        modelAndView.addObject("users", this.userService.users());
        modelAndView.setViewName("layout/users");
        return modelAndView;
    }

    // Profile // https://localhost:8080/atm/profile
    @GetMapping("/profile/{id}")
    public String profile(Model model, @PathVariable("id") Long id){
        model.addAttribute("user", this.userService
                .findById(id));
        return "layout/profile";
    }





    // User's Transactions// localhost:8080/atm/transactions

    // Admin side home page
}
