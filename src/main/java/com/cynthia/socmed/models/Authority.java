package com.cynthia.socmed.models;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private AuthorityType name;



}