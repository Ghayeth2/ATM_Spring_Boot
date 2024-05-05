package com.atm.controllers.handlers;

import com.atm.services.abstracts.UserService;
import com.atm.core.exceptions.EmailExistsException;
import com.atm.models.dtos.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/atm/auth")
@Log4j2
public class HAuthController {


    private UserService userService;

    @Autowired
    public HAuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String signup(@Valid @ModelAttribute("user") UserDto userDto
                , BindingResult bindingResult, Model model
                        , RedirectAttributes redirectAttributes) throws EmailExistsException {

        if(!userDto.getPassword().equals(userDto.getPassword2()))
            return "redirect:/atm/registration?notMatched";
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDto);
//            log.error("An error occurred!");
            return "layout/auth/signup";
        }
//        log.info("Success " + userDto);
//        try {
//            userService.save(userDto);
//            redirectAttributes.addFlashAttribute("success",
//                    "User registration successful");
//        } catch (EmailExistsException ex) {
//            redirectAttributes.addFlashAttribute("Error: ", ex.getMessage());
//        }
        userService.save(userDto);

        return "redirect:/atm/registration";
    }
}
