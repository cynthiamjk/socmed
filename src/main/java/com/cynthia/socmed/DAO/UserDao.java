package com.cynthia.socmed.DAO;

import com.cynthia.socmed.models.FriendRequest;
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


        public User findByEmail (String email);


   public User findByResetToken(String resetToken);



        public boolean existsByUsername (String username);

    public boolean existsByEmail(String email);

    public boolean existsByResetToken(String resetToken);



       public long count();




  /*  @Transactional
    @Modifying
    @Query(value = "SELECT received_id FROM user_received WHERE  user_id=?1", nativeQuery = true)
    List<Integer> findReceived(@Param("user_id") int userId);*/




}
