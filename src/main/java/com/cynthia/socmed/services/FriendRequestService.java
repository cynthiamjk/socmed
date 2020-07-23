package com.cynthia.socmed.services;


import com.cynthia.socmed.DAO.FriendRequestDao;
import com.cynthia.socmed.DAO.UserDao;
import com.cynthia.socmed.models.FriendRequest;
import com.cynthia.socmed.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class FriendRequestService {

    @Autowired
    FriendRequestDao friendRequestDao;

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    public void deleteFriendrequest(FriendRequest fr, User u) {
        List<FriendRequest> usersFriendRequests = friendRequestDao.findAllByDest(u);
        Iterator itr = usersFriendRequests.iterator();
        while (itr.hasNext()) {
            FriendRequest y = (FriendRequest) itr.next();
            if (y.getId() == fr.getId())
                itr.remove();
        }
        friendRequestDao.deleteById(fr.getId());
    }

    public FriendRequest createFriendRequest(User sender,String username) {
        List<Integer> dests = friendRequestDao.findFL(sender);
        User dest = userDao.findByUsername(username);
        List<User> friends = userService.getFriendship(sender);
        if (sender != dest) {
            // check if a friendRequest hasnt been sent to this user yet
            if (!dests.contains(dest.getId())) {
                //check if both arent friends already
                if (!friends.contains(dest)) {
                    FriendRequest fr = new FriendRequest();
                    fr.setSender(sender);
                    fr.setDest(dest);
                    return friendRequestDao.save(fr);
                }
            }

        }
        return null;
    }

    public List<FriendRequest> findAllByDest(User dest) {
        return friendRequestDao.findAllByDest(dest);
    }

    public FriendRequest findById(int id) {
        return friendRequestDao.findById(id);
    }

}
