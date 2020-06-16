package com.cynthia.socmed.controllers;

import com.cynthia.socmed.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping(value ="/")
    public String index (ModelMap model) {
     
        return "book";

    }
}