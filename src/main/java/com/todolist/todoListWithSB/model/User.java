package com.todolist.todoListWithSB.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class User {

    private long id;

    private String name;

    private String email;

    private boolean deleted;

    private List<Long> tasks;


    @JsonCreator
    public User(@JsonProperty(required = false) long id, String name, String email, boolean deleted, List<Long> tasks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.deleted = deleted;
        this.tasks = tasks;
    }

    public User() {
    }

    public List<Long> getTasks() {
        return tasks;
    }

    public void setTasks(List<Long> tasks) {
        this.tasks = tasks;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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