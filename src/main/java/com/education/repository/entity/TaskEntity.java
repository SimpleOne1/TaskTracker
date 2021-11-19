package com.education.repository.entity;

import javax.persistence.*;
import java.util.List;
@Entity
public class TaskEntity {
    private static final int START_SEQ = 100000;
    @Id
    @SequenceGenerator(name="global_seq_task",sequenceName = "global_seq_task",allocationSize = 1,initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "global_seq_task")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PROJECT_ID")
    private ProjectEntity project;

    @Column(nullable = false)
    private TaskType type;

    @OneToMany
    @JoinTable(
            name="CHILD_TASKS",
            joinColumns = {@JoinColumn(name="TASK_ID",referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name="CHILD_ID",referencedColumnName = "ID",unique = true)}
    )
    private List<TaskEntity> childTasks;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Long reporterId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ASSIGNEE_USER")
    private UserEntity assignee;//можно сделать ссылкой на пользователя

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public List<TaskEntity> getChildTasks() {
        return childTasks;
    }

    public void setChildTasks(List<TaskEntity> childTasks) {
        this.childTasks = childTasks;
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

    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(Long reporterId) {
        this.reporterId = reporterId;
    }

    public UserEntity getAssignee() {
        return assignee;
    }

    public void setAssignee(UserEntity assignee) {
        this.assignee = assignee;
    }
}
