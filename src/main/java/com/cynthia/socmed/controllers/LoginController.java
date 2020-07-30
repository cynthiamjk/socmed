package com.cynthia.socmed.controllers;

import com.cynthia.socmed.models.User;
import com.cynthia.socmed.services.EmailService;
import com.cynthia.socmed.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@SessionAttributes("user")
public class LoginController {

    @Autowired
    UserService userService;


    @Autowired
    private EmailService emailService;





    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(
            @RequestParam(name="email", required = true) String email,
            @RequestParam(name="password", required = true) String password,

            ModelMap model, RedirectAttributesModelMap redirectAttributesModelMap) {
        //obviously we find the User thanks to his email
        User u = userService.findByEmail(email);
        //if it's found, it means that User is not null so..
        if (u != null) {
            // we check if the email matches with the pw and the generated salt
            if (u.getPassword().endsWith(BCrypt.hashpw(password, u.getSalt()))) {
                // Password match -> log the user in
                //adds the User attributes to the model
                model.addAttribute("user", u);



                //display friend
          /*    List<User> friends = u.getFriends();
                for (User friend : friends) {
                    model.addAttribute("friends", friends);

                }*/
                System.out.println("logged in" + u.getId());
                // log file




                return "redirect:profile";


            }

        }
        redirectAttributesModelMap.addFlashAttribute("error", "Invalid password or email!");
        return "redirect:/";
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public String index(HttpServletRequest request, ModelMap model) {
        return "forgotPassword";
    }

    // Process form submission from forgotPassword page
    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("email") String email, HttpServletRequest request) {

        // Lookup user in database by e-mail
        User user = userService.findByEmail(email);

        if (!userService.existsByEmail(email)) {
            modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
        } else {

            // Generate random 36-character string token for reset password

            user.setResetToken(UUID.randomUUID().toString());

            // Save token to database
            userService.save(user);

            String appUrl = request.getScheme() + "://" + request.getServerName();

            // Email message
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("cynthia1778@hotmail.fr");
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                    + ":8089/reset?token=" + user.getResetToken());

            emailService.sendEmail(passwordResetEmail);

            // Add success message to view
            modelAndView.addObject("successMessage", "A password reset link has been sent to " + email);
        }

        modelAndView.setViewName("forgotPassword");
        return modelAndView;

    }

    // Display form to reset password
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {



        if (userService.existsByResetToken(token)) { // Token found in DB
            modelAndView.addObject("resetToken", token);
            System.out.println(token);
        } else { // Token not found in DB
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
        }

        modelAndView.setViewName("resetPassword");
        return modelAndView;
    }

    // Process reset password form
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ModelAndView setNewPassword(ModelAndView modelAndView,
                                       @RequestParam("resetToken") String token,
                                       @RequestParam("password") String password,
                                       RedirectAttributes redir) {

        // Find the user associated with the reset token
        User user = userService.findByResetToken(token);

        System.out.println(token);

        // This should always be non-null but we check just in case
        if (userService.existsByUsername(user.getUsername())) {

            //Set new password
            String salt = BCrypt.gensalt();
            user.setSalt(salt);
            user.setPassword(BCrypt.hashpw(password, salt));

            // Set the reset token to null so it cannot be used again
            user.setResetToken(null);

            // Save user
            userService.save(user);

            // In order to set a model attribute on a redirect, we must use
            // RedirectAttributes
            redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");

            modelAndView.setViewName("redirect:/");
            return modelAndView;

        } else {
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
            modelAndView.setViewName("redirect:resetPassword");
        }

        return modelAndView;
    }

    // Going to reset page without a token redirects to login page
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
        return new ModelAndView("redirect:homePage");
    }
}