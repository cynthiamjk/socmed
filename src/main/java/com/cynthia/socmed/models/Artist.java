package com.cynthia.socmed.models;

import lombok.Data;

import javax.persistence.*;
import java.io.File;
import java.util.List;

@Entity
@Data
public class Artist {

    @Id
    @GeneratedValue
    private int id;
    private String name;

    private File picture;
    @OneToMany
    private List<Style> styles;
    @OneToMany
    private List<Event> events;
  /*  @OneToOne
    private Country country;
*/


}
