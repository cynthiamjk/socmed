package com.cynthia.socmed.models;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @OneToOne
    private User sender;
    @ManyToOne
    private User user;
    @ManyToOne
    private Post post;
    @OneToOne
    private ReportObject reportObject;


    public Report(int id, LocalDate date, User sender, User user, Post post, ReportObject reportObject) {
        this.id = id;
        this.date = date;
        this.sender = sender;
        this.user = user;
        this.post = post;
        this.reportObject = reportObject;
    }

    public Report() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }



    public ReportObject getReportObject() {
        return reportObject;
    }

    public void setReportObject(ReportObject reportObject) {
        this.reportObject = reportObject;
    }



    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", date=" + date +
                ", sender=" + sender +
                ", user=" + user +
                ", post=" + post +
                ", reportObject=" + reportObject +

                '}';
    }
}
