package com.cynthia.socmed.services;

import com.cynthia.socmed.DAO.ReportObjectDao;
import com.cynthia.socmed.models.ReportObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportObjectService {

   @Autowired
    ReportObjectDao reportObjectDao;

    public List<ReportObject> findAll() {
        return reportObjectDao.findAll();
    }

    public ReportObject findByName(String name) {
        return reportObjectDao.findByname(name);

    }



}
