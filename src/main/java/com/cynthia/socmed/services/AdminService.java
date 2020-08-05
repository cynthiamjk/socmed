package com.cynthia.socmed.services;


import com.cynthia.socmed.DAO.ReportDao;
import com.cynthia.socmed.models.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
public class AdminService {

    @Autowired
    ReportDao reportDao;


    public List<Report> getPostReports(int id) {
        if (id == 1) {
            List<Report> reports = (List<Report>) reportDao.findAll();
            List<Integer> posts = new ArrayList<>();

            List<Report> postReports = new ArrayList<>();
            for (Report report : reports) {
                if (report.getPost() != null) {
                    postReports.add(report);
                }
            }
            for (Report postReport : postReports) {
                posts.add(postReport.getPost().getId());

            }

            return postReports;
        }
        return null;
    }

    public Map<Integer, Long> postReportOccurences () {

        List<Report> reports = (List<Report>) reportDao.findAll();
        List<Integer> posts = new ArrayList<>();

        List<Report> postReports = new ArrayList<>();
        for (Report report : reports) {
            if (report.getPost() != null) {
                postReports.add(report);
            }
        }
        for (Report postReport : postReports) {
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
        return sorted;
    }

}
