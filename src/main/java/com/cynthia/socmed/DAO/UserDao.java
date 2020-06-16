package com.cynthia.socmed.DAO;

import com.cynthia.socmed.models.FriendRequest;
import com.cynthia.socmed.models.Post;
import com.cynthia.socmed.models.Role;
import com.cynthia.socmed.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


public interface UserDao extends JpaRepository<User, Integer> {

        public User findById(int id);

        public User findByUsername (String username);

        //public List<User> findByUsername(String username);

        public User findByEmail (String email);

        public boolean existsByUsername (String username);

        public long count();

    public List<User> findAllByOrderByIdDesc();


    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.role=?1 WHERE u.id = ?2")
    int updateRole(@Param("role") Role role, @Param("id") int id);




    @Transactional
    @Modifying
    @Query(value ="delete FROM user_received where received_id =?1", nativeQuery = true)
    void deleteFr(@Param("received") int received);

    @Transactional
    @Modifying
    @Query(value = "SELECT received_id FROM user_received WHERE  user_id=?1", nativeQuery = true)
    List<Integer> findReceived(@Param("user_id") int userId);

    @Transactional
    @Modifying
    @Query(value = "SELECT liked_posts_id FROM user_liked_posts WHERE  user_id=?1", nativeQuery = true)
    List<Integer> findLiked(@Param("user_id") int userId);

}
