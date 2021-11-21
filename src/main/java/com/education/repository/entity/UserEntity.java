package com.education.repository.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@NamedQueries(
        @NamedQuery(name=UserEntity.GET_ALL,query = "SELECT u from UserEntity u")
)

public class UserEntity {
    public static final int START_SEQ = 100;
    public static final String GET_ALL="Users.getAll";

    @Id
    @SequenceGenerator(name="global_seq_user",sequenceName = "global_seq_user",allocationSize = 1,initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "global_seq_user")
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private  String password;
    @Column
    private String role;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id",insertable = false,updatable = false)
    @JsonBackReference
    private  TeamEntity team;

    @OneToMany(mappedBy = "assignee",cascade = CascadeType.ALL)
    private List<TaskEntity> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "reporter",cascade = CascadeType.ALL)
    private List<TaskEntity> createdTasks = new ArrayList<>();

    @Column
    private boolean deleted = Boolean.FALSE;

    public Long getId() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TaskEntity> getCreatedTasks() {
        return createdTasks;
    }

    public void setCreatedTasks(List<TaskEntity> createdTasks) {
        this.createdTasks = createdTasks;
    }

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    public String getRole() {
        return role;
    }

    public void setRoles(String role) {
        this.role = role;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
