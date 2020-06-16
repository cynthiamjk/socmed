package com.cynthia.socmed.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private User author;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String text;
    private File picture;
    private int likes;
    private boolean isLiked;
    private boolean isPrivate;

}
