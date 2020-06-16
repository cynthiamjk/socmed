package com.cynthia.socmed.DAO;

import com.cynthia.socmed.models.Post;
import com.cynthia.socmed.models.Role;
import com.cynthia.socmed.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface PostDao extends JpaRepository<Post, Integer> {

    public Post findById(int id);

    public List<Post> findByAuthor(User user);

    public Post findByDate(Date date);

    public List<Post> findAllByOrderByIdDesc();



}
