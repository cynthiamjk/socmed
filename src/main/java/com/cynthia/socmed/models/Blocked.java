package com.cynthia.socmed.models;


import javax.persistence.*;

@Entity

public class Blocked {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private User blockedUser;

    public Blocked() {
    }

    public Blocked(int id, User user, User blockedUser) {
        this.id = id;
        this.user = user;
        this.blockedUser = blockedUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getBlockedUser() {
        return blockedUser;
    }

    public void setBlockedUser(User blockedUser) {
        this.blockedUser = blockedUser;
    }

    @Override
    public String toString() {
        return "Blocked{" +
                "id=" + id +
                ", user=" + user +
                ", blockedUser=" + blockedUser +
                '}';
    }
}
