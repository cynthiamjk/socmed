package com.cynthia.socmed.models;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Blocked {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private User blockedUser;


}
