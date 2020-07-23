package com.cynthia.socmed.services;


import com.cynthia.socmed.DAO.FriendshipDao;
import com.cynthia.socmed.models.Friendship;
import com.cynthia.socmed.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipService {

    @Autowired
    FriendshipDao friendshipDao;

    @Autowired
    UserService userService;


    public Friendship addFriendship (User dest, User sender) {
        Friendship fs = new Friendship();
        fs.setUser1(dest);
        fs.setUser2(sender);
        return friendshipDao.save(fs);
    }

    public boolean areFriends (User item, User u) {
        List<User> currentUsersFriends = userService.getFriendship(u);
        List<User> otherUsersFriends = userService.getFriendship(item);
        if (currentUsersFriends.contains(item) && otherUsersFriends.contains(u))
            return true;
        return false;
    }

    public void delete (Friendship friendship) {
        friendshipDao.delete(friendship);
    }

}
