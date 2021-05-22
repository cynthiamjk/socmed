package com.cynthia.socmed.controllers;

import com.cynthia.socmed.DAO.ReportDao;
import com.cynthia.socmed.comp.UserValidator;
import com.cynthia.socmed.models.*;
import com.cynthia.socmed.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("user")
public class UserController {

    @Autowired
    PostService postService;

    @Autowired
    CountryService countryService;

    @Autowired
    UserService userService;

    @Autowired
    LikesService likesService;

    @Autowired
    UserValidator userValidator;

    @Autowired
    FriendshipService friendshipService;

    @Autowired
    FriendRequestService friendRequestService;

    @Autowired
    EmojiService emojiService;

    @Autowired
    ReportObjectService reportObjectService;

    @Autowired
    ReportDao reportDao;


    @RequestMapping(value = "/profile", method = RequestMethod.GET)

    public String index(@RequestParam(value="search", required =false) String username,
                        ModelMap model, User u) {

        //helps check if a post is liked by the user, display css on like button
        List<Post> likedPosts = userService.likedPost(u) ;
        model.addAttribute("likedPosts", likedPosts);

        //display all user and friends' posts in desc order
        Post [] usersPostsArray =  postService.showAllPosts(u);
        model.addAttribute("userPosts", usersPostsArray);

        List<FriendRequest> received = friendRequestService.findAllByDest(u);
        model.addAttribute("received", received);

        List<User> friends = userService.getFriendship(u);
        model.addAttribute("friends", friends);

        List <Emojis> emojis = (List<Emojis>) emojiService.findAll();
        model.addAttribute("emojis", emojis);
        
        model.addAttribute("allUsers", userService.thatDidNotBlockMe(u));

        List <String> friendsName =userService.friendsNames(friends);
        model.addAttribute("friendsName", friendsName);

        List<ReportObject> reportObjects = reportObjectService.findAll();
        model.addAttribute("reportObjects", reportObjects);

        return "profile";
    }


    @RequestMapping(value = "/addEvent", method = RequestMethod.GET)
    public String eventForm(ModelMap model, User u) {
        List<Country> countries = countryService.findAll();
        model.addAttribute("countries", countries);
        if (u.getId() == 0) {
            return "redirect:/";
        }
        return "addEvent";
    }



    @RequestMapping(value = "/userSettings", method = RequestMethod.GET)
    public String settings(ModelMap model, User u) {
        List<Country> countries = countryService.countries();
        model.addAttribute("countries", countries);

        if (u.getId() == 0) {
            return "redirect:/";
        }

        return "userSettings";
    }


    @RequestMapping(value = "/searchAction", method = RequestMethod.GET)
    public String uResult(@RequestParam(value="item", required =false) String username,
                          @RequestParam(name="action", defaultValue = "") String action,
                          ModelMap model, User u, RedirectAttributesModelMap redirectAttributesModelMap) {

        User item = userService.findByUsername(username);
        if(userService.principalIsBlocked(u, item)) {
            item = null;
        }
        List<User> friends = userService.getFriendship(u);
        List <String> friendsName =userService.friendsNames(friends);
        List<User> users = userService.findAll();
        List<User> notBlocked = new ArrayList<>();
        for(User us : users) {
            if(!userService.principalIsBlocked(u, item)) {
                notBlocked.add(us);
            }
        }
        switch(action) {
            case "visitProfile":
                if (friendshipService.areFriends(item, u)) {
                    model.addAttribute("item", item);
                    List<Post> likedPosts = userService.likedPost(u);
                    model.addAttribute("likedPosts", likedPosts);
                    model.addAttribute("imagePath", countryService.countryFlag(item));
                    model.addAttribute("countryName", item.getCountry().getName());
                    model.addAttribute("userPosts", postService.showFriendsPosts(item));
                    return "friendsProfile";
                }
                break;
            case "userList":
                List<String> blockedUsers = userService.blockedUsers(u);
                model.addAttribute("blockedUsers", blockedUsers);
                model.addAttribute("allUsers", users);
                model.addAttribute("friendsName", friendsName);
                model.addAttribute("friends", friends);
                if(item != null) {
                    model.addAttribute("item", item);
                    return "userList";
                } else {
                    redirectAttributesModelMap.addFlashAttribute("userNotFound", "User doesn't exist!");

                }

            default:
        }

        return "redirect:profile";
    }


    @RequestMapping(value = "/friendsAction", method = RequestMethod.GET)
    public String fProfile( @RequestParam(value="item", required =false) String username,
                            @RequestParam(name="action", defaultValue = "") String action,
                            ModelMap model, User u) {
        User item = userService.findByUsername(username);

        switch(action) {
            case "visitProfile":
                if (friendshipService.areFriends(item, u)) {
                    model.addAttribute("item", item);
                    List<Post> likedPosts = userService.likedPost(u);
                    model.addAttribute("likedPosts", likedPosts);
                    model.addAttribute("imagePath", countryService.countryFlag(item));
                    model.addAttribute("countryName", item.getCountry().getName());
                    model.addAttribute("userPosts", postService.showFriendsPosts(item));
                    List<User> users = userService.findAll();
                    model.addAttribute("allUsers", users);
                    return "friendsProfile";
                }
                break;

            case "chat":
                break;

            case "mute":
                break;

            case "rmFriend":
                userService.removeFriend(u, item);
                break;

            case "block":
                userService.blockUser(item, u);
                userService.removeFriend(u, item);
                break;

            default:

        }

        return "redirect:profile";
    }

    @RequestMapping(value = "/updateUsername", method = RequestMethod.POST)
    public String updateUser(@RequestParam(value="username", required = false) String username,
                             User u, RedirectAttributesModelMap redirectAttributesModelMap, BindingResult bindingResult) {
        userValidator.validateEditUsername(u, bindingResult);
        if(bindingResult.hasErrors()){
            redirectAttributesModelMap.addFlashAttribute("usernameE", bindingResult.getFieldError().getDefaultMessage());
        } else {
            userService.save(u);
            redirectAttributesModelMap.addFlashAttribute("update", "Updated successfully");
        }
        return "redirect:userSettings";
    }

    @RequestMapping(value = "/updateEmail", method = RequestMethod.POST)
    public String updateEmail(@RequestParam(value="email", required = false) String email,
                              User u, RedirectAttributesModelMap redirectAttributesModelMap, BindingResult bindingResult) {
        userValidator.validateEditEmail(u, bindingResult);
        if(bindingResult.hasErrors()){
            redirectAttributesModelMap.addFlashAttribute("email", bindingResult.getFieldError().getDefaultMessage());
        } else {
            userService.save(u);
            redirectAttributesModelMap.addFlashAttribute("updateEmail", "Updated successfully");
        }
        return "redirect:userSettings";
    }

    @RequestMapping(value = "/updateCountry", method = RequestMethod.POST)
    public String editForm(@RequestParam(value="countries", required = false) String country,
                           ModelMap model, User user,RedirectAttributesModelMap redirectAttributesModelMap) {
        List<Country> countries = countryService.countries();
        model.addAttribute("countries", countries);
        Country userCountry = countryService.findByName(country);
        userService.updateUserCountry(userCountry,user);
        redirectAttributesModelMap.addFlashAttribute("country", "Country updated!");
        return "redirect:userSettings";

    }

    @RequestMapping(value = "/editPicture", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String editPicture(@RequestParam(value="editPicture", required = false) MultipartFile editPicture,
                              User user, RedirectAttributesModelMap redirectAttributesModelMap) throws IOException {

        userService.editProfilePicture(editPicture, user);
        redirectAttributesModelMap.addFlashAttribute("picture", "Picture updated!");
        return "redirect:userSettings";


    }



    @RequestMapping(value = "/privacy", method = RequestMethod.POST)
    public String privacySettings(User u, RedirectAttributesModelMap redirectAttributesModelMap) {
        userService.save(u);
        redirectAttributesModelMap.addFlashAttribute("privacy", "Privacy settings updated!");
        return "redirect:userSettings";
    }



    @RequestMapping(value = "passwordChange", method = RequestMethod.POST)
    public String pwChange(
            @RequestParam(name = "oldPassword") String oldPassword,
            @RequestParam(name = "newPassword") String newPassword,
            @RequestParam(name = "passwordc", defaultValue = "") String passwordc,
            User u, RedirectAttributesModelMap redirectAttributesModelMap) {

        if (u.getPassword().endsWith(BCrypt.hashpw(oldPassword, u.getSalt()))
                && (!newPassword.isEmpty()) && newPassword.equals(passwordc)) {
            userService.passwordReset(u, oldPassword, newPassword, passwordc);
            redirectAttributesModelMap.addFlashAttribute("password", "Password successfully changed");

        } else {
            redirectAttributesModelMap.addFlashAttribute("err", "Incorrect password or password length");

        }
        return "redirect:userSettings";
    }

    @RequestMapping(value = "/deleteAccount", method = RequestMethod.POST)
    public String deleteAccount(User u, RedirectAttributesModelMap redirectAttributesModelMap) {
        userService.delete(u);
        redirectAttributesModelMap.addFlashAttribute("delete", "Your account have been successfully deleted, farewell!");
        return "redirect:/";
    }


    @RequestMapping(value = "/blockUser", method = RequestMethod.POST)
    public String blocklist(ModelMap model) {

        return "redirect:userSettings";
    }




    @RequestMapping(value = "/addFriend", method = RequestMethod.POST)
    public String addFriend(
            @RequestParam(value = "item", required = false) String username,
            Model model) {
        System.out.println(username);
        User sender = (User) model.getAttribute("user");

        friendRequestService.createFriendRequest(sender, username);
        return "redirect:profile";


    }

    @RequestMapping(value = "/removeFriend", method = RequestMethod.POST)
    public String rmFr(  @RequestParam(value = "username", required = false) String username,
                         ModelMap model) {
        User currentUser = (User) model.getAttribute("user");
        userService.removeFriend(currentUser, userService.findByUsername(username));
        return "redirect:profile";
    }

    @RequestMapping(value = "/friendRequests", method = RequestMethod.GET)
    public String showFrs(ModelMap modelMap, User u) {
        List<FriendRequest> frs = friendRequestService.findAllByDest(u);
        modelMap.addAttribute("frs", frs);
        return "friendRequests";
    }

    @RequestMapping(value = "/friendRequests", method = RequestMethod.POST)
    public String acceptFrs(@RequestParam(name = "action", defaultValue = "") String action,
                            ModelMap modelMap, int id) {

        User dest = (User) modelMap.getAttribute("user");
        FriendRequest fr = friendRequestService.findById(id);
        User sender = fr.getSender();

        switch (action) {
            case "acceptFr": friendRequestService.deleteFriendrequest(fr, dest);
                friendshipService.addFriendship(dest, sender);
                break;
            default:
                friendRequestService.deleteFriendrequest(fr, dest);
        }
        return "friendRequests";
    }



    @RequestMapping(value = "/postStuff", method = RequestMethod.POST)
    public String postStuff(@RequestParam(name = "uPost", defaultValue = "") String uPost,
                            @RequestParam(value="file1", required = false) MultipartFile picture,
                            Model model, User u, RedirectAttributesModelMap redirectAttributesModelMap) throws IOException {
        Post p =  postService.generatePost(uPost, u.getUsername());
        if(!picture.isEmpty()) {
            postService.addPostPicture(picture, p);
        }
        if (postService.postExists(p) == false) {
            redirectAttributesModelMap.addFlashAttribute("emptyPost", "Can't submit an empty post");
            return "redirect:profile";
        } else {
            postService.save(p);
            model.addAttribute("uPost", uPost);
            return "redirect:profile";
        }
    }


    @RequestMapping(value="/like", method = RequestMethod.POST)
    public String likePost(@RequestParam(name="id") int id,
                           ModelMap modelMap) {
        User u = (User) modelMap.getAttribute("user");
        List<Likes> likedPosts = postService.likePosts(id, u);
        modelMap.addAttribute("likedPost", likedPosts);

        return "redirect:profile";
    }


    @RequestMapping(path = "/deletePost", method = RequestMethod.GET)
    public String deletePostWithModal(@RequestParam(name="id1") int postId,
                                      @RequestParam(name="author1") String author,
                                      ModelMap modelMap) {
        User u = (User) modelMap.getAttribute("user");
        Post p = postService.findById(postId);
       postService.deletePost(p, u, author);
        return "redirect:profile";
    }

    @RequestMapping(path = "/findPostToDelete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Post findPostToDelete(@PathVariable("id") int postId) {
        Post p =postService.findById(postId);
        List <Likes> likes = (List<Likes>) likesService.findAllByPost(p);
        if(!likes.isEmpty()) {
            for (Likes l : likes) {
                likesService.delete(l);
            }
        }
        if (p.getLinkPreview() != null) {
            p.setLinkPreview(null);
        }

        return p;
    }



    @RequestMapping(value = "/updatePost", method = RequestMethod.POST)
    public String updatePostWithModal(@RequestParam(name="id", required = false) int id,
                                      @RequestParam(name = "text", defaultValue = "") String text,
                                      @RequestParam(name = "defaultText", defaultValue = "") String defaultText,
                                      ModelMap modelMap) {

        User u = (User) modelMap.getAttribute("user");
        Post p = postService.findById(id);
        postService.updatePost(u.getUsername(), p, text);
        if(postService.postExists(p) == false) {
            p.setText(defaultText);
        }
        postService.save(p);
        return "redirect:profile";
    }

    @RequestMapping(value= "/reportPost", method = RequestMethod.POST)
    public String reportPost (@RequestParam(name="id2") int id,
                              @RequestParam(name="reportObject") String reportObjectName,
                              ModelMap modelMap, User u) {
        Post p =postService.findById(id);
        ReportObject rep = reportObjectService.findByName(reportObjectName);
        Report report = new Report();
        report.setDate(LocalDate.now());
        report.setSender(u);
        report.setPost(p);
        report.setReportObject(rep);
        reportDao.save(report);
        return "redirect:profile";
    }

    @RequestMapping(path = "/findPost/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Post findPost(@PathVariable("id") int postId) {
        return postService.findById(postId);
    }

    @RequestMapping(path = "/findPostToReport/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Post findPostToReport(@PathVariable("id") int postId) {

        return postService.findById(postId);
    }




}












