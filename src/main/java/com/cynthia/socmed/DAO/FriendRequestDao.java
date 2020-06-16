package com.cynthia.socmed.DAO;

import com.cynthia.socmed.models.FriendRequest;
import com.cynthia.socmed.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface FriendRequestDao  extends CrudRepository<FriendRequest, Integer> {



   public FriendRequest findById(int id);


    public FriendRequest findBySender(int id);


   @Transactional
    @Modifying
    @Query("SELECT sender.id FROM FriendRequest WHERE dest =?1")
    List<Integer> findDestFL(@Param("dest") User dest);



    @Transactional
    @Modifying
    @Query("SELECT dest.id FROM FriendRequest WHERE sender =?1")
    List<Integer> findFL(@Param("sender") User sender);

 @Transactional
 @Modifying
 @Query("SELECT id FROM FriendRequest WHERE sender =?1 and dest=?2")
 List<Integer> findFL2(@Param("sender") User sender, @Param("dest") User dest);

 @Transactional
 @Modifying
 @Query("SELECT id FROM FriendRequest WHERE  dest=?2")
 List<Integer> findReceived(@Param("dest") User dest);

 @Transactional
 @Modifying
 @Query(value ="delete FROM friend_request where id =?1", nativeQuery = true)
 void deleteFrs(@Param("id") int id);


}
