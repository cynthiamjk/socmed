package com.cynthia.socmed.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;
    private String passwordc;
    private String salt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;
    private int age;
   @ManyToOne
    private Country country;
    private File profilePicture;
    private String profilePicturePath;
    @ManyToMany
    private List <Post> likedPosts;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<User> friends;

    @ManyToOne
    private Role role ;

    @ManyToMany
    private List<User> blocked;
    @OneToMany
    private List <Event> events;


    private String resetPassword;
    private String resetToken;
    @OneToOne
    private ConfirmationToken confirmationToken;
    private boolean friendListIsPublic;
    private boolean eventIsPublic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +

                ", birthdate=" + birthdate +
                ", profilePicture=" + profilePicture +
                ", country=" + country +
                ", resetPassword='" + resetPassword + '\'' +
                ", confirmationToken=" + confirmationToken +
                ", friendListIsPublic=" + friendListIsPublic +
                ", eventIsPublic=" + eventIsPublic +
                '}';
    }
}
