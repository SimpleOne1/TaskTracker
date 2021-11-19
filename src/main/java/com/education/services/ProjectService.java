package com.education.services;

import com.education.repository.ProjectToCreate;
import com.education.repository.entity.ProjectEntity;
import com.education.repository.entity.TeamEntity;
import com.education.repository.repo.ProjectRepository;
import com.education.repository.repo.TeamRepository;
import com.education.services.exceptions.ProjectNotFoundException;
import com.education.services.exceptions.TeamNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;

    public ProjectService(ProjectRepository projectRepository, TeamRepository teamRepository) {
        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
    }

    public ProjectEntity get(Long id){
      ProjectEntity project=projectRepository.get(id);
      if(project == null){
          throw new ProjectNotFoundException(id);
      }
      return project;
    }

    public List<ProjectEntity> getAll(){
        return projectRepository.getAll();
    }

    public ProjectEntity create(ProjectToCreate projectToCreate){
        ProjectEntity project = new ProjectEntity();
        project.setName(projectToCreate.getName());
        return projectRepository.create(project);
    }

    public ProjectEntity edit(Long id,ProjectToCreate project){
        ProjectEntity p = projectRepository.get(id);
        p.setName(project.getName());
        return projectRepository.create(p);
    }

    public ProjectEntity addTeam(Long projectId,Long teamId){
        if(teamRepository.get(teamId)==null){
            throw new TeamNotFoundException(teamId);
        }
        return projectRepository.adTeam(projectId,teamId);
    }

    public ProjectEntity delTeam(Long projectId,Long teamId){
        if(teamRepository.get(teamId)==null){
            throw new TeamNotFoundException(teamId);
        }
        return projectRepository.delTeam(projectId,teamId);
    }
}
