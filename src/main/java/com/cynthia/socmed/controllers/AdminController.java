package com.cynthia.socmed.controllers;


import com.cynthia.socmed.DAO.ReportDao;
import com.cynthia.socmed.models.Report;
import com.cynthia.socmed.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes("user")
public class AdminController {


    @Autowired
    ReportDao reportDao;

    @RequestMapping(value = "/adminPage", method = RequestMethod.GET)
    public String admin(User u, ModelMap modelMap) {
        if (u.getId() == 0 || u.getId() != 1) {
            return "redirect:/";
        }

        List<Report> reports = (List<Report>) reportDao.findAll();
        int n = reports.size();
        modelMap.addAttribute("number", n);
        return "adminPage";
    }

}
