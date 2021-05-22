package com.cynthia.socmed.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User dest;

    public FriendRequest() {
    }

    public FriendRequest(int id, User sender, User dest) {
        this.id = id;
        this.sender = sender;
        this.dest = dest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getDest() {
        return dest;
    }

    public void setDest(User dest) {
        this.dest = dest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FriendRequest)) return false;
        FriendRequest that = (FriendRequest) o;
        return dest.equals(that.dest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dest);
    }
}


