package com.cynthia.socmed.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Friend {
    @Id
    @GeneratedValue
    private int id;
    private String username;
    private String path;


}
