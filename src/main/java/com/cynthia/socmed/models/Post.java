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
public class Post {

    @Id
    @GeneratedValue
    private int id;

    private String author;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String text;
    private File picture;
    private String url;
    @OneToOne
    private LinkPreview linkPreview;
    private String embedLink;
    private String picturePath;
    @OneToMany
    private List<Likes> userLikes;
    private int likes;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return id == post.id &&
                likes == post.likes &&
                Objects.equals(author, post.author) &&
                Objects.equals(date, post.date) &&
                Objects.equals(text, post.text) &&
                Objects.equals(picture, post.picture) &&
                Objects.equals(linkPreview, post.linkPreview) &&
                Objects.equals(embedLink, post.embedLink) &&
                Objects.equals(userLikes, post.userLikes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, date, text, picture, linkPreview, embedLink, userLikes, likes);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id + '}';
    }
}
