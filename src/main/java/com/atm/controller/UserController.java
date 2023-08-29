package com.atm.controller;

import com.atm.business.abstracts.UserRegister;
import com.atm.business.abstracts.UserService;
import com.atm.core.exception.EmailExistsException;
import com.atm.model.dtos.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/atm/user")
@Log4j2
public class UserController {


    private UserService userService;
    @Qualifier("userNameExistsDecorator")
    @Autowired
    private UserRegister userRegister;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("user") UserDto userDto
                , BindingResult bindingResult, Model model
                        , RedirectAttributes redirectAttributes){

        if(!userDto.getPassword().equals(userDto.getPassword2()))
            return "redirect:/atm/registration?notMatched";
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDto);
            log.error("An error occurred!");
            return "layout/signup";
        }
        log.info("Success " + userDto);
        try {
            userRegister.save(userDto);
            redirectAttributes.addFlashAttribute("success",
                    "User registration successful");
        } catch (EmailExistsException ex) {
            redirectAttributes.addFlashAttribute("exception", ex.getMessage());
        }

        return "redirect:/atm/registration";
    }
}
