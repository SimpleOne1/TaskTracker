package com.education.repository;

import java.util.List;

public class TeamCreating {
    private String name;
    private List<Long> userIds;
    private List<Long> projectIds;

    public TeamCreating(String name) {
        this.name = name;
    }

    public TeamCreating(String name, List<Long> userIds, List<Long> projectIds) {
        this.name = name;
        this.userIds = userIds;
        this.projectIds = projectIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public List<Long> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Long> projectIds) {
        this.projectIds = projectIds;
    }
}
