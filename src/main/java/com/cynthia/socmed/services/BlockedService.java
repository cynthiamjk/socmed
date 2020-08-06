package com.cynthia.socmed.services;

import com.cynthia.socmed.DAO.BlockedDao;
import com.cynthia.socmed.models.Blocked;
import com.cynthia.socmed.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockedService {

    @Autowired
    BlockedDao blockedDao;

    public List<Blocked> findByUser(User u) {
        return  blockedDao.findByUser(u);

    }

    public List<Blocked> findByBlockedUser(User u) {
        return  blockedDao.findByBlockedUser(u);
    }
}
