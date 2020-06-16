package com.cynthia.socmed.controllers;


import com.cynthia.socmed.DAO.UserDao;

import com.cynthia.socmed.models.Role;
import com.cynthia.socmed.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Controller
@SessionAttributes("user")
public class RegisterController {

    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(
            @RequestParam (name = "username",required = true, defaultValue = "") String username,
            @RequestParam (name = "email", required = true, defaultValue = "") String email,
            @RequestParam(name="password", required = true, defaultValue = "") String password,
            @RequestParam(name="passwordc", required = true, defaultValue = "") String passwordc,
            @RequestParam(name="birthdate", required = true, defaultValue = "")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthdate,
            ModelMap model,
    LocalDate ld) {
        ld = LocalDate.now();
        if ((!password.isEmpty()) && password.equals(passwordc) && (Period.between(birthdate, ld).getYears() >= 15 )) {

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            String salt = BCrypt.gensalt();
            user.setSalt(salt);
            user.setPassword(BCrypt.hashpw(password, salt));
            user.setBirthdate(birthdate);
            user.setEventIsPublic(true);
            user.setFriendListIsPublic(true);
            user = userDao.save(user);
            System.out.println(user.getUsername());

            if (user.getId() == 1) {
                userDao.updateRole(Role.valueOf("ADMIN"), user.getId());
            } else  {
                userDao.updateRole(Role.valueOf("VISITOR"), user.getId());
            }

            return "redirect:/";
        }
        return "redirect:/";
    }

}


