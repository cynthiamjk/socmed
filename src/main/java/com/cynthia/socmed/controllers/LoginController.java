package com.cynthia.socmed.controllers;

import com.cynthia.socmed.DAO.PostDao;
import com.cynthia.socmed.DAO.UserDao;
import com.cynthia.socmed.models.Post;
import com.cynthia.socmed.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@SessionAttributes("user")
public class LoginController {

    @Autowired
    UserDao userDao;

    @Autowired
    PostDao postDao;



    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(
            @RequestParam(name="email", required = true) String email,
            @RequestParam(name="password", required = true) String password,

            ModelMap model) {
        //obviously we find the User thanks to his email
        User u = userDao.findByEmail(email);
        //if it's found, it means that User is not null so..
        if (u != null) {
            // we check if the email matches with the pw and the generated salt
            if (u.getPassword().endsWith(BCrypt.hashpw(password, u.getSalt()))) {
                // Password match -> log the user in
                //adds the User attributes to the model
                model.addAttribute("user", u);
                //display friend list
                List<User> friends = u.getFriends();
                for (User friend : friends) {
                    model.addAttribute("friends", friends);

                }
                System.out.println("logged in" + u.getId());
                // log file

                return "redirect:profile";

                }

            }
        model.addAttribute("error", "not valid");
        return "book";
    }
}