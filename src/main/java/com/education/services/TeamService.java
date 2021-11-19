package com.education.services;

import com.education.repository.ProjectToCreate;
import com.education.repository.TeamCreating;
import com.education.repository.entity.ProjectEntity;
import com.education.repository.entity.TeamEntity;
import com.education.repository.entity.UserEntity;
import com.education.repository.repo.ProjectRepository;
import com.education.repository.repo.TeamRepository;
import com.education.repository.repo.UserRepository;
import com.education.services.exceptions.ProjectNotFoundException;
import com.education.services.exceptions.TeamNotFoundException;
import com.education.services.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TeamService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;

    public TeamService(UserRepository userRepository, TeamRepository teamRepository,ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.projectRepository=projectRepository;
    }

    public TeamEntity create(TeamCreating team){
        TeamEntity teamEntity = new TeamEntity();
        List<Long> usersInTeam = team.getUserIds();
        if(usersInTeam != null){
            for(Long id : usersInTeam){
                UserEntity user = userRepository.get(id);
                if(user == null){
                    throw new UserNotFoundException(id);
                }
                if(!Objects.equals(user.getRole(), "manager") && !Objects.equals(user.getRole(), "admin"))
                {
                    teamEntity.getMembers().add(userRepository.get(id));
                }
            }
        }
        List<Long> projects = team.getProjectIds();
        if(projects!=null){
            for(Long id : projects){
                if(projectRepository.get(id)==null){
                    throw new ProjectNotFoundException(id);
                }
                teamEntity.getProjects().add(projectRepository.get(id));
            }
        }
        teamEntity.setName(team.getName());
        return teamRepository.create(teamEntity);
    }

    public TeamEntity edit(Long id, TeamCreating team){
       TeamEntity entity = teamRepository.get(id);
       entity.setName(team.getName());
       return teamRepository.create(entity);
    }

    public void delete(Long id) throws Exception{
        TeamEntity entity = teamRepository.get(id);
        if(entity==null){
            throw new TeamNotFoundException(id);
        }
        if(entity.getProjects()!=null && entity.getMembers()!=null){
            teamRepository.delete(id);
        }
        else{
            throw new Exception("Can't delete team with active members");
        }
    }

    public TeamEntity get(Long id) {
        TeamEntity entity = teamRepository.get(id);
        if(entity==null){
            throw new TeamNotFoundException(id);
        }
        return entity;
    }

    public List<TeamEntity> getAll(){
        return teamRepository.getAll();
    }
}
