package com.cynthia.socmed.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class Timeline {

    @Id
    @GeneratedValue
    private int id;
    @OneToMany
    private List<Post> posts;
}
