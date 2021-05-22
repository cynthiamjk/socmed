package com.cynthia.socmed.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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

    public Friendship() {
    }

    public Friendship(int id, User user1, User user2) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id=" + id +
                ", user1=" + user1 +
                ", user2=" + user2 +
                '}';
    }
}
