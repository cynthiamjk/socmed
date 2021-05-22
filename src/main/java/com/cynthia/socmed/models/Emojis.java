package com.cynthia.socmed.models;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Emojis {

    @Id
    @GeneratedValue

    private int id;
    private String code;

    public Emojis() {
    }

    public Emojis(int id, String code) {
        this.id = id;
        this.code = code;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Emojis{" +
                "id=" + id +
                ", code='" + code + '\'' +
                '}';
    }
}
