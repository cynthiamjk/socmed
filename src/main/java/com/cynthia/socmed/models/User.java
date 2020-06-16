package com.cynthia.socmed.models;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class User {



    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;

    private String password;
    private String salt;
    private boolean isFriend;
    private int requestNumber;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;

  /*  @ManyToOne
    private Country country;*/
    private File profilePicture;
    /*@OneToMany
    private List <Post> posts;*/
    /*@OneToMany
    private List <Post> likedPosts;*/
    @ManyToMany
    private List<User> friends;
    /*@OneToMany
    private List<Likes> likes;*/


   @OneToMany(fetch = FetchType.EAGER)
    private List<FriendRequest> received;
    @ManyToMany
    private List<User> blocked;
    @OneToMany
    private List <Event> events;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany
    private List<Style> styles;
    private String resetPassword;
    private boolean isEnabled;
    @OneToOne
    private Timeline timeline;
    private boolean isBlocked;


    @OneToOne
    private ConfirmationToken confirmationToken;
    private boolean friendListIsPublic;
    private boolean eventIsPublic;


    public boolean isFriendListIsPublic() {
        return friendListIsPublic;
    }

    public void setFriendListIsPublic(boolean friendListIsPublic) {
        this.friendListIsPublic = friendListIsPublic;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", isFriend=" + isFriend +
                ", requestNumber=" + requestNumber +
                ", birthdate=" + birthdate +

                ", profilePicture=" + profilePicture +
                ", events=" + events +
                ", role=" + role +
                ", styles=" + styles +
                ", resetPassword='" + resetPassword + '\'' +
                ", isEnabled=" + isEnabled +
                ", timeline=" + timeline +
                ", isBlocked=" + isBlocked +
                ", confirmationToken=" + confirmationToken +
                ", friendListIsPublic=" + friendListIsPublic +
                ", eventIsPublic=" + eventIsPublic +
                '}';
    }
}
