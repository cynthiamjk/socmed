package com.cynthia.socmed.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Likes)) return false;
        Likes likes = (Likes) o;
        return post.equals(likes.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post);
    }

    @Override
    public String toString() {
        return "Likes{" +
                "post=" + post +
                '}';
    }
}


