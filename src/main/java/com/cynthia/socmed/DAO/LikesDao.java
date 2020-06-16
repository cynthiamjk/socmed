package com.cynthia.socmed.DAO;

import com.cynthia.socmed.models.Likes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface LikesDao extends CrudRepository<Likes, Integer> {

    @Transactional
    @Modifying
    @Query(value = "SELECT post_id FROM likes WHERE  user_id=?1", nativeQuery = true)
    List<Integer> findPostsLikedByUserId(@Param("user_id") int userId);

}
