package com.cynthia.socmed.models;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data

public class LinkPreview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String siteName;
    private String domain;
    private String url;
    private String title;
     private String description;
     private String image;
     private String imageAlt;
 /*    @OneToOne
    private Post post;*/



}