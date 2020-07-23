package com.cynthia.socmed.DAO;

import com.cynthia.socmed.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PostDao extends JpaRepository<Post, Integer> {

    public Post findById(int id);

    public List<Post> findByAuthor(String username);

    public Post findByDate(Date date);

    public List<Post> findAllByOrderByIdDesc();



}
