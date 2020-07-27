package com.cynthia.socmed.DAO;

import com.cynthia.socmed.models.ReportObject;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReportObjectDao extends CrudRepository<ReportObject, Integer> {


    public List<ReportObject> findAll();

    public ReportObject findByname(String name);
}
