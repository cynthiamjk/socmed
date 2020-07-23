package com.cynthia.socmed.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToOne
    private LinkPreview linkPreview;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @DateTimeFormat(pattern = "HH-mm")
    private LocalTime timeStart;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
    @DateTimeFormat(pattern = "HH-mm")
    private LocalTime timeEnd;
    private File eventPicture;
    private String picturePath;
    private String url;

    @ManyToMany
    private List<User> participants;
    @ManyToOne
    private Country location;

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", linkPreview=" + linkPreview +
                ", startDate=" + startDate +
                ", timeStart=" + timeStart +
                ", endDate=" + endDate +
                ", timeEnd=" + timeEnd +
                ", eventPicture=" + eventPicture +
                ", participants=" + participants +
                ", location=" + location +
                '}';
    }
}
