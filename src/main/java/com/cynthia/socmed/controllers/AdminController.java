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

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

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
        List<Report> postReports = new ArrayList<>();
        List<Report> userReports = new ArrayList<>();
        List<Report> eventReports = new ArrayList<>();

        for(Report report: reports) {
            if (report.getEvent() != null) {
                eventReports.add(report);
            }
            if (report.getPost() != null) {
                postReports.add(report);
            }
            if (report.getUser() != null) {
                userReports.add(report);
            }
        }

        int postNumber = postReports.size();
        int eventNumber = eventReports.size();
        int userNumber = userReports.size();
        modelMap.addAttribute("postNumber", postNumber);
        modelMap.addAttribute("eventNumber", eventNumber);
        modelMap.addAttribute("userNumber", userNumber);
        return "adminPage";
    }

    @RequestMapping(value = "/postReports", method = RequestMethod.GET)

    public String postReports (User u, ModelMap modelMap) {
        if (u.getId() == 1) {
            List<Report> reports = (List<Report>) reportDao.findAll();
            List<Integer> posts =  new ArrayList<>();

            List<Report> postReports = new ArrayList<>();
            for (Report report : reports) {
                if (report.getPost() != null) {
                    postReports.add(report);
                }
            }
            for( Report postReport : postReports) {
                posts.add(postReport.getPost().getId());

            }

            Map<Integer, Long> counts = posts.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

            Map<Integer, Long> sorted = counts
                    .entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .collect(
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                    LinkedHashMap::new));
            modelMap.addAttribute("sorted", sorted);
            modelMap.addAttribute("postReports", postReports);
            return "postReports";
        }
return "redirect:/";

    }

}
