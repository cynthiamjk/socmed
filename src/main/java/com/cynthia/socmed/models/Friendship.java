package com.cynthia.socmed.models;

import lombok.Data;
import lombok.Generated;

import javax.persistence.*;

@Entity
@Data
public class Friendship {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;
}
