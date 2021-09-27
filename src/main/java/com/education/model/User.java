package com.education.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class User {

    private Long id;

    private String name;

    private String email;

    private boolean deleted;


    @JsonCreator
    public User(@JsonProperty(required = false) long id, String name, String email, boolean deleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.deleted = deleted;
    }

    public User() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
