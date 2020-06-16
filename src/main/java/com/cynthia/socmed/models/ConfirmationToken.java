package com.cynthia.socmed.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
public class ConfirmationToken {

    @Id
    @GeneratedValue
    private int id;
    private String token;
    @OneToOne
    private User user;
}
