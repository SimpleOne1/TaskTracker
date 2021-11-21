package com.education.repository.repo;

import com.education.repository.entity.TaskEntity;
import com.education.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class TaskRepository {

    @PersistenceContext
    private EntityManager em;

    public TaskEntity save(TaskEntity task) {
        if (task.getId() == null) {
            em.persist(task);
            return task;
        } else {
            return em.merge(task);
        }
    }

    public TaskEntity get(Long id) {
        return em.find(TaskEntity.class, id);
    }

    public List<TaskEntity> getAll() {
        return em.createQuery("Select t from TaskEntity t", TaskEntity.class).getResultList();
    }

    public TaskEntity setAssignee(Long taskId, Long assigneeId) {
        TaskEntity reference = em.getReference(TaskEntity.class, taskId);
        UserEntity userReference = em.getReference(UserEntity.class, assigneeId);
        reference.setAssignee(userReference);
        em.merge(reference);
        return reference;

    }

}
