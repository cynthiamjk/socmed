package com.cynthia.socmed.services;

import com.cynthia.socmed.DAO.LikesDao;
import com.cynthia.socmed.models.Likes;
import com.cynthia.socmed.models.Post;
import com.cynthia.socmed.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikesService  {

    @Autowired
    LikesDao likesDao;


    public List<Likes> findAllByUser(User user) {
        return likesDao.findAllByUser(user);
    }


    public List<Likes> findAllByPost(Post p) {
        return likesDao.findAllByPost(p);
    }


    public boolean existsByPost(Post p) {
        return likesDao.existsByPost(p);
    }


    public Likes findByPost(Post p) {
        return likesDao.findByPost(p);
    }



    public Likes findByUser(User u) {
        return likesDao.findByUser(u);
    }


    public Likes save(Likes l) {
        return likesDao.save(l);
    }



    public Likes findById(int id) {
        return likesDao.findById(id);
    }


    public boolean existsById(Integer integer) {
        return likesDao.existsById(integer);
    }


    public List<Likes> findAll() {
        return (List<Likes>) likesDao.findAll();
    }


    public void deleteById(Integer integer) {
      likesDao.deleteById(integer);
    }


    public void delete(Likes likes) {
  likesDao.delete(likes);
    }




}
