package com.cynthia.socmed.services;

import com.cynthia.socmed.DAO.*;
import com.cynthia.socmed.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserService  {
    @Autowired
    UserDao userDao;

    @Autowired
    LikesDao likesDao;

    @Autowired
    FriendshipDao friendshipDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    BlockedDao blockedDao;



    public void registerUser(User user) {
        List<User> users = userDao.findAll();

        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        String salt = BCrypt.gensalt();
        user.setSalt(salt);
        user.setPassword(BCrypt.hashpw(user.getPassword(), salt));
        user.setPasswordc(null);
        user.setBirthdate(user.getBirthdate());
        user.setCountry(user.getCountry());
        user.setAge(Period.between(user.getBirthdate(), LocalDate.now()).getYears());
        user.setFriendListIsPublic(true);


        if (users.isEmpty()) {
            user.setRole(roleDao.findByName("ADMIN"));
        } else {
            user.setRole(roleDao.findByName("USER"));
        }
        userDao.save(user);
    }

    public List<Post> likedPost(User user) {
        List<Likes> liked = likesDao.findAllByUser(user);
        List<Post> likedPosts = new ArrayList<>() ;
        for ( Likes l : liked) {
            likedPosts.add(l.getPost());
        }
        return likedPosts;
    }


    public void updateUserCountry(Country c, User u) {
        u.setCountry(c);
        userDao.save(u);
    }

    public void editProfilePicture(MultipartFile file, User user) throws IOException {

        File convertFile = new File("C:\\Users\\CynthiaM\\Desktop\\socmed\\images\\"+user.getUsername()+".jpg");
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(String.valueOf(convertFile));
        fout.write(file.getBytes());
        fout.close();
        user.setProfilePicture(convertFile);
        user.setProfilePicturePath("images/"+user.getUsername()+".jpg");
        userDao.save(user);

    }

    public void passwordReset(User u, String oldPassword, String newPassword, String passwordc) {
        if (u.getPassword().endsWith(BCrypt.hashpw(oldPassword, u.getSalt()))
                && (!newPassword.isEmpty()) && newPassword.equals(passwordc)) {
            String salt = BCrypt.gensalt();
            u.setSalt(salt);
            u.setPassword(BCrypt.hashpw(newPassword, salt));
            u.setPasswordc(null);
            userDao.save(u);
        }
    }

    public List<User> getFriendship (User u) {
        List <Friendship> friendships = (List<Friendship>) friendshipDao.findAll();
        List <Friendship> userFriendships =new ArrayList<>();
        List<User> friends =new ArrayList<>();
        for (Friendship friendship : friendships) {
            if (friendship.getUser1().getId() == u.getId() || friendship.getUser2().getId() == u.getId()) {
                userFriendships.add(friendship);
            }
        }

        for (Friendship userfriendship : userFriendships) {
            if(userfriendship.getUser1().getId() != u.getId()) {
                friends.add(userfriendship.getUser1());
            }
            if(userfriendship.getUser2().getId() != u.getId())
                friends.add(userfriendship.getUser2());
        }
        return friends;
    }

    public void removeFriend (User u, User friendToRemove) {
        List <Friendship> friendships = (List<Friendship>) friendshipDao.findAll();
        List <Friendship> userFriendships =new ArrayList<>();
        for (Friendship friendship : friendships) {
            if (friendship.getUser1().getId() == u.getId() || friendship.getUser2().getId() == u.getId()) {
                userFriendships.add(friendship);
            }
        }
        for (Friendship userfriendship : userFriendships) {
            if(userfriendship.getUser1().getId() == friendToRemove.getId()) {
                friendshipDao.deleteById(userfriendship.getId());
            }
            if(userfriendship.getUser2().getId() == friendToRemove.getId())
                friendshipDao.deleteById(userfriendship.getId());
        }

    }

    public List<String> friendsNames (List<User> friends) {
        List<String> friendsName = new ArrayList<>();
        for (User us : friends) {
            friendsName.add(us.getUsername());
        }
        return friendsName;
    }



    public List<User> thatDidNotBlockMe (User u){
        List<User> cleanList = userDao.findAll();
        List<User> theOnesThatBlockedPrincipal = new ArrayList<>();
        List<Blocked> blockedList = blockedDao.findByBlockedUser(u);
        Iterator itr = cleanList.iterator();
          for (Blocked bl : blockedList) {
              theOnesThatBlockedPrincipal.add(bl.getUser());
          }
          while(itr.hasNext()) {
              User y = (User) itr.next();
              if(theOnesThatBlockedPrincipal.contains(y))
                  itr.remove();
          }


return cleanList;
    }


    public List<String> blockedUsers (User u) {
        List<Blocked> blockedList = blockedDao.findByUser(u);
        List<String> blockedUserNames = new ArrayList<>();
        for (Blocked blocked : blockedList) {
            blockedUserNames.add(blocked.getBlockedUser().getUsername());
        }
        return  blockedUserNames;
    }

    public boolean principalIsBlocked (User u, User otherUser) {
        List<Blocked> blockedList = blockedDao.findByUser(otherUser);
        for (Blocked blocked : blockedList) {
            if (blocked.getBlockedUser().getId() == u.getId()) {
                return true;
            }
        }
        return false;
    }

    public void blockUser(User theOneIWantToBlock, User u) {
        List<Blocked> blockedList = blockedDao.findByUser(u);
        if(!blockedList.isEmpty()) {
            for (Blocked blocked : blockedList) {
                if (blocked.getBlockedUser().getId() != theOneIWantToBlock.getId()) {
                    Blocked block = new Blocked();
                    block.setUser(u);
                    block.setBlockedUser(theOneIWantToBlock);
                    blockedDao.save(block);
                }
            }
        } else {
            Blocked block = new Blocked();
            block.setUser(u);
            block.setBlockedUser(theOneIWantToBlock);
            blockedDao.save(block);
        }
    }

    public void save(User u) {
        userDao.save(u);
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }


    public User findByEmail(String email) {

        return userDao.findByEmail(email);
    }


    public User findByResetToken(String resetToken) {

        return userDao.findByResetToken(resetToken);
    }

    public boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }


    public boolean existsByResetToken(String resetToken) {

        return userDao.existsByResetToken(resetToken);
    }

    public boolean existsById(int id) {
        return userDao.existsById(id);
    }

    public boolean existsByUsername(String username) {
        return userDao.existsByUsername(username);
    }


    public List<User> findAll() {
        return userDao.findAll();
    }


    public void deleteById(int id) {
        userDao.deleteById(id);
    }


    public User findById (int id) {
        return userDao.findById(id);}

    public void delete(User u) {
        userDao.delete(u);}




}

