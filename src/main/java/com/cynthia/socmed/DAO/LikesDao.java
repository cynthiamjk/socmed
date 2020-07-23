package com.cynthia.socmed.DAO;

import com.cynthia.socmed.models.Likes;
import com.cynthia.socmed.models.Post;
import com.cynthia.socmed.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LikesDao extends CrudRepository<Likes, Integer> {

  public Likes findByUser (User u );

  public Likes findById (int id );

  public List<Likes> findAllByUser (User u);

    List<Likes> findAllByPost(Post p);

    public boolean existsByPost(Post p);

  public Likes findByPost(Post p);

  public Integer deleteByPost (Post p);


}
