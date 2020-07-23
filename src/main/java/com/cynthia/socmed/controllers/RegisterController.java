package com.cynthia.socmed.controllers;

import com.cynthia.socmed.comp.UserValidator;
import com.cynthia.socmed.models.User;
import com.cynthia.socmed.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("user")
public class RegisterController {


    @Autowired
    UserService userService;

    @Autowired
    UserValidator userValidator;


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@ModelAttribute("userForm") User u,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        userValidator.validate(u, bindingResult);
        if (bindingResult.hasErrors()) {
           redirectAttributes.addFlashAttribute("err", bindingResult.getFieldError().getDefaultMessage());
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return "redirect:/";
        } else {
            userService.registerUser(u);
            redirectAttributes.addFlashAttribute("okSignUp", "You registered, you can now log in to your account! :)");
            return "redirect:/";
        }

    }
}



