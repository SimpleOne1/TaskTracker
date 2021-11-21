package com.education.repository.repo;

import com.education.repository.entity.ProjectEntity;
import com.education.repository.entity.TeamEntity;
import com.education.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class ProjectRepository {
    @PersistenceContext
    private EntityManager em;

    public ProjectEntity get(Long id) {
        return em.find(ProjectEntity.class, id);
    }

    public List<ProjectEntity> getAll() {
        return em.createQuery("Select p from ProjectEntity p", ProjectEntity.class).getResultList();
    }

    public ProjectEntity create(ProjectEntity projectEntity) {
        if (projectEntity.getId() == null) {
            em.persist(projectEntity);
            return projectEntity;
        } else {
            return em.merge(projectEntity);
        }
    }

    public ProjectEntity adTeam(Long projectId, Long teamId) {
        ProjectEntity reference = em.getReference(ProjectEntity.class, projectId);
        TeamEntity team = em.getReference(TeamEntity.class, teamId);
        reference.getTeams().add(team);
        em.merge(reference);
        return reference;
    }

    public ProjectEntity delTeam(Long projectId, Long teamId) {
        ProjectEntity reference = em.getReference(ProjectEntity.class, projectId);
        TeamEntity team = em.getReference(TeamEntity.class, teamId);
        reference.getTeams().remove(team);
        em.merge(reference);
        return reference;
    }


}
