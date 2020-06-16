package com.cynthia.socmed.controllers;

import com.cynthia.socmed.DAO.*;
import com.cynthia.socmed.misc.PostTimeComparator;
import com.cynthia.socmed.models.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

@Controller
@SessionAttributes("user")


public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    FriendRequestDao friendRequestDao;

    @Autowired
    PostDao postDao;

    @Autowired
    LikesDao likesDao;

    @Autowired
    CountryDao countryDao;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String index(HttpServletRequest request, ModelMap model, User u, Integer count) {
        List<User> friends = u.getFriends();
        for (User friend : friends) {
            model.addAttribute("friends", friends);

        }
        int requestNumber = u.getRequestNumber();
        model.addAttribute("requestNumber", requestNumber);
        List<Post> posts = (List<Post>) postDao.findAll();


        List<User> fris = u.getFriends();
        List<Post> userPosts = new ArrayList<Post>();
        for ( Post po : posts) {
            if(po.getAuthor().getUsername().equals(u.getUsername())) {
                userPosts.add(po);
            }

        }
        for (User fri : fris) {
            for (Post p : postDao.findByAuthor(fri)) {
                userPosts.add(p);
            }
        }
        Post[] osef = new Post[userPosts.size()];
        Post[] usersPostsArray = userPosts.toArray(osef);
        Arrays.sort(usersPostsArray, new PostTimeComparator());

        model.addAttribute("userPosts", usersPostsArray);
        return "profile";
    }

    @RequestMapping(value = "/adminPage", method = RequestMethod.GET)
    public String admin(ModelMap model, User u) {
        if (u.getId() == 0 || u.getId() != 1) {
            return "redirect:/";
        }
        return "adminPage";
    }

    @RequestMapping(value = "/userSettings", method = RequestMethod.GET)
    public String settings(ModelMap model, User u) {
        if (u.getId() == 0) {
            return "redirect:/";
        }

        return "userSettings";
    }

    @RequestMapping(value = "/friendsProfile", method = RequestMethod.GET)
    public String fProfile( @RequestParam(value="item", required =false) String username,
                            ModelMap model, User u) {

        model.addAttribute("item", userDao.findByUsername(username));
        User friend = userDao.findByUsername(username);
        System.out.println(friend.getUsername());

        return "friendsProfile";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String editForm(ModelMap model, @Valid User u) {
        userDao.save(u);
        return "userSettings";

    }
    //add countries to database
    @RequestMapping(value = "/addCountries", method = RequestMethod.POST)
    public String addCountries(ModelMap model) {
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:/Users/CynthiaM/Desktop/socmed/src/main/resources/static/countriesOk.txt"));

            while ((line =br.readLine()) != null) {
                Country c = new Country ();
                c.setName(line);
                countryDao.save(c);
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("successMessage", "Countries have been successfully added to your database");
        return "adminPage";
    }

    @RequestMapping(value = "/privacy", method = RequestMethod.POST)
    public String privacySettings(ModelMap model, User u) {
        userDao.save(u);
        return "userSettings";
    }


    @RequestMapping(value = "/passwordChange", method = RequestMethod.GET)
    public String pwForm(ModelMap model) {
        return "passwordChange";
    }

    @RequestMapping(value = "passwordChange", method = RequestMethod.POST)
    public String pwChange(
            @RequestParam(name = "oldPassword", required = true) String oldPassword,
            @RequestParam(name = "newPassword", required = true) String newPassword,
            @RequestParam(name = "passwordc", required = true, defaultValue = "") String passwordc,
            ModelMap modelMap,
            User u) {
        System.out.println("salut");
        if (u.getPassword().endsWith(BCrypt.hashpw(oldPassword, u.getSalt()))
                && (!newPassword.isEmpty()) && newPassword.equals(passwordc)) {
            System.out.println("pw changed ");
            String salt = BCrypt.gensalt();
            u.setSalt(salt);
            u.setPassword(BCrypt.hashpw(newPassword, salt));
            u = userDao.save(u);
            System.out.println("pw changed success");
        }

        return "userSettings";
    }


    @RequestMapping(value = "/blocklist", method = RequestMethod.GET)
    public String blocklist(ModelMap model) {

        return "userSettings";
    }

    @RequestMapping(value = "/searchUser", method = RequestMethod.GET)
    public String showUserByName(@RequestParam(value = "search", required = false) String username, Model model, User u) {
        model.addAttribute("search", userDao.findByUsername(username));
        List<User> friends = u.getFriends();
        List<String> fName = new ArrayList<>();
        for (User friend : friends) {
            fName.add(friend.getUsername());

        }

        model.addAttribute("fName", fName);
        return "searchUser";
    }


    @RequestMapping(value = "/addFriend", method = RequestMethod.POST)
    public String addFriend(
            @RequestParam(value = "search", required = false) String username,
            @RequestParam(name="action", defaultValue = "") String action,
            Model model) {
        User sender = (User) model.getAttribute("user");
        assert sender != null;
        List<User> friends = sender.getFriends();
        List<FriendRequest> frs = (List<FriendRequest>) friendRequestDao.findAll();
        List<Integer> dests = friendRequestDao.findFL(sender);
        User dest = userDao.findByUsername(username);
        int destId = dest.getId();
        int rn = dest.getRequestNumber();
        rn = rn + 1;
        // check if sender != from dest of friendrequest
        switch (action) {
            case "sendFriendRequest":
                if (destId != sender.getId()) {
                    // check if a friendRequest hasnt been sent to this user yet
                    if (!dests.contains(destId)) {
                        //check if both arent friends already
                        if (!friends.contains(dest)) {
                            FriendRequest fr = new FriendRequest();
                            fr.setSender(sender);
                            fr.setDest(dest);
                            fr.setSent(true);
                            fr.setAccepted(false);
                            dest.getReceived().add(fr);
                            friendRequestDao.save(fr);
                            dest.setRequestNumber(rn);
                            userDao.save(sender);


                            //  return "profile";
                        }
                    }

                }
                break;
            default:
                System.out.println("salut");

        }
        return "redirect:profile";

    }
    @RequestMapping(value = "/friendRequests", method = RequestMethod.GET)
    public String showFrs(ModelMap modelMap, User u) {
        List<Integer> is = userDao.findReceived(u.getId());
        List<FriendRequest>frs = new ArrayList<FriendRequest>();
        for( int fr: is) {
            frs.add(friendRequestDao.findById(fr));
            modelMap.addAttribute("frs", frs);

        }
        return "friendRequests";
    }

    @RequestMapping(value = "/friendRequests", method = RequestMethod.POST)
    public String acceptFrs(

            @RequestParam(name="action", defaultValue = "") String action,
            ModelMap modelMap, FriendRequest fr,   int id) {

        User dest = (User) modelMap.getAttribute("user");
        System.out.println(fr.getId());

        int rn = dest.getRequestNumber();
        rn = rn - 1;


        switch (action) {
            case "acceptFr":
                fr = friendRequestDao.findById(id);
                User sender = fr.getSender();
                System.out.println(sender.getUsername() + "  " + dest.getUsername());
                dest.getFriends().add(sender);
                sender.getFriends().add(dest);
                //fr.setAccepted(true);
                //fr.setSent(false);
                dest.setRequestNumber(rn);
                userDao.save(dest);
                userDao.deleteFr(fr.getId());
                friendRequestDao.deleteById(fr.getId());

                break;

            default:
                fr = friendRequestDao.findById(id);
                fr.setDeleted(true);
                fr.setAccepted(false);
                dest.getReceived().remove(fr);
                dest.setRequestNumber(rn);
                userDao.save(dest);
                userDao.deleteFr(fr.getId());
                friendRequestDao.deleteById(fr.getId());
        }

        List<Integer> is = userDao.findReceived(dest.getId());
        List<FriendRequest>frs = new ArrayList<FriendRequest>();
        for( int f: is) {
            frs.add(friendRequestDao.findById(f));
            modelMap.addAttribute("frs", frs);

        }
        return "friendRequests";
    }



    @RequestMapping(value = "/postStuff", method = RequestMethod.POST)
    public String postStuff(@RequestParam(name="uPost", defaultValue = "")String uPost,
                            Model model, LocalDate locaDate, User u) {

        locaDate = LocalDate.now();
        Post p = new Post();
        p.setText(uPost);
        p.setAuthor(u);
        p.setDate(locaDate);
        userDao.save(u);
        postDao.save(p);


        return "redirect:profile";
    }

    @RequestMapping(value="/likeOrShare", method = RequestMethod.POST)
    public String likeOrShare(@RequestParam(name="id") int id,
                              ModelMap modelMap) {
        User u = (User) modelMap.getAttribute("user");

        Post p = postDao.findById(id);
        int likes = p.getLikes();
        int addLikes = likes + 1;
        int suppLikes = likes - 1;

        List<Likes> ls = (List<Likes>) likesDao.findAll();
        List <Likes> userLikes = new ArrayList<>();
        for ( Likes lls : ls) {
            if(lls.getUser().getId() == u.getId())
                userLikes.add(lls);
        }
        if(!userLikes.isEmpty()) {
            for (Likes lks : userLikes) {
                if (lks.getPost().getId() == p.getId()) {
                    p.setLikes(suppLikes);
                    likesDao.deleteById(lks.getId());
                } else {
                    Likes l = new Likes();
                    l.setPost(p);
                    l.setUser(u);
                    p.setLikes(addLikes);
                    likesDao.save(l);

                }
            }
        }
        else {
            Likes l = new Likes();
            l.setPost(p);
            l.setUser(u);
            p.setLikes(addLikes);
            likesDao.save(l);

        }


        return "redirect:profile";
    }
}











