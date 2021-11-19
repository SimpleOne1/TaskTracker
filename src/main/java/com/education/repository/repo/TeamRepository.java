package com.education.repository.repo;

import com.education.repository.entity.ProjectEntity;
import com.education.repository.entity.TeamEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class TeamRepository {
    @PersistenceContext
    private EntityManager em;

    public TeamEntity get(Long id){
        return em.find(TeamEntity.class,id);
    }

    public List<TeamEntity> getAll(){
        return em.createQuery("Select t from TeamEntity t",TeamEntity.class).getResultList();
    }

    public TeamEntity create(TeamEntity teamEntity){//todo add different dto for editing with only name changing
        if(teamEntity.getId()==null){
            em.persist(teamEntity);
            return teamEntity;
        }
        else{
            return em.merge(teamEntity);
        }
    };

    public void delete(Long id){
        TeamEntity reference = em.getReference(TeamEntity.class,id);
        em.remove(reference);
    }

}
