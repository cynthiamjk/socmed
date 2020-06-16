package com.cynthia.socmed.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.File;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Event {

    @Id
    @GeneratedValue
    private int id;
    private String eventName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private File eventPicture;
    @ManyToMany
    private List<Artist> artists;
    @ManyToMany
    private List<User> participants;
   /* @OneToOne
    private Country location;*/

}
