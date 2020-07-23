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

    public FriendRequest findBySender(String s);

   public List<FriendRequest> findAllByDest(User dest);

   public FriendRequest save(FriendRequest friendRequest);

   public void delete(FriendRequest friendRequest);



    @Transactional
    @Modifying
    @Query("SELECT dest.id FROM FriendRequest WHERE sender =?1")
    List<Integer> findFL(@Param("sender") User sender);



}
