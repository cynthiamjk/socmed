package com.cynthia.socmed.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;



}
