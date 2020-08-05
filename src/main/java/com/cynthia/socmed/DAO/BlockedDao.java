package com.cynthia.socmed.DAO;

import com.cynthia.socmed.models.Blocked;
import com.cynthia.socmed.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BlockedDao extends CrudRepository<Blocked, Integer> {




    public List<Blocked> findByUser(User u);
}
