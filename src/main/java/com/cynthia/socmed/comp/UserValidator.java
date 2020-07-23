package com.cynthia.socmed.comp;

import com.cynthia.socmed.DAO.UserDao;
import com.cynthia.socmed.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;


@Component
public class UserValidator implements Validator {

    @Autowired
    UserDao userDao;

    @Override
    public boolean supports(Class<?> aClass) {

        return User.class.equals(aClass);
    }


    @Override
    public void validate(Object o, Errors errors) {

        User user = (User) o;
        LocalDate ld = LocalDate.now();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userDao.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username", "User already exists");
        }

       if(!user.getUsername().matches("[\\w*\\s*]*")) {
            errors.rejectValue("username", "bl", "Your username should only contains letters, numbers and spaces!");

        }

        if (userDao.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.userForm.email", "Email already used");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password", "Password must contains at least 8 characters");
        }

        if (Period.between(user.getBirthdate(), ld).getYears() < 15) {
            errors.rejectValue("birthdate", "TOO_YOUNG", "Too young");
        }

        if(!(user.getPassword().equals(user.getPasswordc()))) {
            errors.rejectValue("password", "nomatch.password", "Passwords don't match");
        }

        ValidationUtils.rejectIfEmpty(errors, "country", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("country", "Size.userForm.password", "enter a country please");
        }

    }

    public void validateEditUsername (Object o, Errors e) {

        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(e, "username", "NotEmpty");
        if (user.getUsername().length() > 32) {
            e.rejectValue("username", "Size.userForm.username");
        }
        if (userDao.findByUsername(user.getUsername()) != user && userDao.findByUsername(user.getUsername()) != null) {
            e.rejectValue("username", "Duplicate.userForm.username", "User already exists");
        }

    }

    public void validateEditEmail(Object o, Errors e) {

        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(e, "email", "NotEmpty");
        if (user.getUsername().length() > 32) {
            e.rejectValue("email", "Size.userForm.email");
        }
        if (userDao.findByEmail(user.getEmail()) != user && userDao.findByEmail(user.getEmail()) != null) {
            e.rejectValue("email", "Duplicate.userForm.email", "Email already exists");
        }

    }




}
