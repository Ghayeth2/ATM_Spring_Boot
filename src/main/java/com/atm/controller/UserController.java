package com.atm.controller;

import com.atm.business.abstracts.UserRegister;
import com.atm.business.abstracts.UserService;
import com.atm.core.exception.EmailExistsException;
import com.atm.model.dtos.UserDto;
import jakarta.persistence.Lob;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    // localhost:8080/atm/user/delete/{id}
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        this.userService.delete(id);
        return "redirect:/atm/users?deleted";
    }

    // https:localhost:8080/atm/user/update/{id}
    @PostMapping("/update/{id}")
    public String update(@PathVariable("id")Long id, @Valid
                    @ModelAttribute("user") UserDto userDto,
                         BindingResult bindingResult, Model model
            , RedirectAttributes redirectAttributes
                         ){
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDto);
            log.info("Errors are: "+ bindingResult.getAllErrors());
            return "layout/profile";
        }
        log.info("Update function in the controller is ACTIVE ");

        this.userService.update(userDto, id);
        redirectAttributes.addFlashAttribute("success",
                "Updated successfully");
        return "redirect:/atm/profile/"+id;
    }

    // localhost:8080/atm/user/makeAdmin/{id}
    @GetMapping("/changeRole/{id}")
    public String changeRole(@PathVariable("id") Long id){
        log.info("Id of the user is:"+id);
        this.userService.changeRole(id);
        return "redirect:/atm/users?roleChanged";
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
