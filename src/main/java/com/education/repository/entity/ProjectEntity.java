package com.education.repository.entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Projects")
public class ProjectEntity {
    private static final int START_SEQ = 20;
    @Id
    @SequenceGenerator(name = "global_seq_project", sequenceName = "global_seq_project", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq_project")
    private Long id;
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "projects")
    private List<TeamEntity> teams;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private List<TaskEntity> tasks = new ArrayList<>();


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

    public List<TeamEntity> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamEntity> teams) {
        this.teams = teams;
    }

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }
}
