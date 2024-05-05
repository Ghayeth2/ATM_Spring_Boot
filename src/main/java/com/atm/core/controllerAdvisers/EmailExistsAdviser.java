package com.atm.core.controllerAdvisers;

import com.atm.core.exceptions.EmailExistsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class EmailExistsAdviser {
    @ExceptionHandler(EmailExistsException.class)
    public String handleEmailExistsException(EmailExistsException ex, RedirectAttributes redirectAttributes) {
        // Set the error message as a flash attribute to pass it back to the registration page
        redirectAttributes.addFlashAttribute("Error: ", ex.getMessage());

        // Redirect back to the registration page without using a full redirect
        return "redirect:/atm/registration";
    }
}
