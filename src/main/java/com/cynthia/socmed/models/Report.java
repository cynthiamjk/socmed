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
    @ManyToOne
    private Event event;

    @OneToOne
    private ReportObject reportObject;
    private boolean isRead;

}
