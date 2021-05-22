package com.cynthia.socmed.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity

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


    public Post() {
    }

    public Post(int id, String author, LocalDate date, String text, File picture,
                String url, LinkPreview linkPreview,
                String embedLink, String picturePath, List<Likes> userLikes, int likes) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.text = text;
        this.picture = picture;
        this.url = url;
        this.linkPreview = linkPreview;
        this.embedLink = embedLink;
        this.picturePath = picturePath;
        this.userLikes = userLikes;
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public File getPicture() {
        return picture;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LinkPreview getLinkPreview() {
        return linkPreview;
    }

    public void setLinkPreview(LinkPreview linkPreview) {
        this.linkPreview = linkPreview;
    }

    public String getEmbedLink() {
        return embedLink;
    }

    public void setEmbedLink(String embedLink) {
        this.embedLink = embedLink;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public List<Likes> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(List<Likes> userLikes) {
        this.userLikes = userLikes;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

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
                "id=" + id +
                ", author='" + author + '\'' +
                ", date=" + date +
                ", text='" + text + '\'' +
                ", picture=" + picture +
                ", url='" + url + '\'' +
                ", linkPreview=" + linkPreview +
                ", embedLink='" + embedLink + '\'' +
                ", picturePath='" + picturePath + '\'' +
                ", userLikes=" + userLikes +
                ", likes=" + likes +
                '}';
    }
}
