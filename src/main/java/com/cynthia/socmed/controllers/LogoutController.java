package com.cynthia.socmed.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("user")
public class LogoutController {

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session, RedirectAttributesModelMap redirectAttributesModelMap) {
        session.invalidate();

        redirectAttributesModelMap.addFlashAttribute("attr", "You're successfully logged out, see you soon! ");
        return "redirect:/";
    }

}