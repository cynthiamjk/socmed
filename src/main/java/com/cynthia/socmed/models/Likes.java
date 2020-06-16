package com.cynthia.socmed.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user.id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "post.id")
    private Post post;


}


