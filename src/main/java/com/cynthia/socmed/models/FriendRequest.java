package com.cynthia.socmed.models;

import lombok.Data;
import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity

@Data
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User dest;

    private boolean sent;
    private boolean received;
    private boolean accepted;
    private boolean deleted;

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}


