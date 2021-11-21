package com.education.repository.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name="teams")
public class TeamEntity {
    private static final int START_SEQ = 1;
    @Id
    @SequenceGenerator(name="global_seq_team",sequenceName = "global_seq_team",allocationSize = 1,initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "global_seq_team")
    private Long id;
    @Column(nullable = false,name="TEAM_NAME")
    private String name;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="team_id")
    @JsonManagedReference
    private List<UserEntity> members = new ArrayList<UserEntity>();


    @ManyToMany
    @JoinTable(
            name="TEAM_PROJECT",
            joinColumns = @JoinColumn(name="TEAM_ID",referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name="PROJECT_ID",referencedColumnName = "ID")
    )
    private List<ProjectEntity> projects = new ArrayList<>();

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

    public List<UserEntity> getMembers() {
        return members;
    }

    public void setMembers(List<UserEntity> members) {
        this.members = members;
    }

    public List<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectEntity> projects) {
        this.projects = projects;
    }
}
