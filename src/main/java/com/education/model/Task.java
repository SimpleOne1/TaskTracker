package com.education.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Task {


    private Long id;

    private String title;

    private String description;

    private Long reporter;

    private Long assignee;

    @JsonCreator
    public Task(long id, String title, String description, Long reporter, Long assignee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reporter = reporter;
        this.assignee = assignee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getReporter() {
        return reporter;
    }

    public void setReporter(Long reporter) {
        this.reporter = reporter;
    }

    public Long getAssignee() {
        return assignee;
    }

    public void setAssignee(Long assignee) {
        this.assignee = assignee;
    }
}
