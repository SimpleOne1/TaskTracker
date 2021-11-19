package com.education.repository.repo;

import com.education.repository.entity.TaskEntity;
import com.education.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class TaskRepository {

    @PersistenceContext
    private EntityManager em;

    public TaskEntity save(TaskEntity task){
        if(task.getId()==null){
            em.persist(task);
            return task;
        }
        else{
            return em.merge(task);
        }
    }

}
