package com.cynthia.socmed.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
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


