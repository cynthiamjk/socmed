package com.cynthia.socmed.services;

import com.cynthia.socmed.DAO.EmojisDao;
import com.cynthia.socmed.models.Emojis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmojiService {

    @Autowired
    EmojisDao emojisDao;


    public Iterable<Emojis> findAll() {
       return emojisDao.findAll();
    }

}
