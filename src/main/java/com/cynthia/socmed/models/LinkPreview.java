package com.cynthia.socmed.models;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data

public class LinkPreview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String siteName;
    private String domain;
    private String url;
    private String title;
     private String description;
     private String image;
     private String imageAlt;


    public LinkPreview() {
    }

    public LinkPreview(int id, String siteName, String domain, String url, String title, String description, String image, String imageAlt) {
        this.id = id;
        this.siteName = siteName;
        this.domain = domain;
        this.url = url;
        this.title = title;
        this.description = description;
        this.image = image;
        this.imageAlt = imageAlt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageAlt() {
        return imageAlt;
    }

    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    @Override
    public String toString() {
        return "LinkPreview{" +
                "id=" + id +
                ", siteName='" + siteName + '\'' +
                ", domain='" + domain + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", imageAlt='" + imageAlt + '\'' +
                '}';
    }
}