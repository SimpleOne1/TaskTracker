package com.education.repository.repo;


import com.education.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public UserEntity save(UserEntity userEntity) {
        if (userEntity.getId() == null) {
            em.persist(userEntity);
            return userEntity;
        } else {
            return em.merge(userEntity);
        }
    }

    public UserEntity get(Long id) {
        return em.find(UserEntity.class, id);
    }

    public List<UserEntity> getAll() {
        return em.createQuery("Select u from UserEntity u WHERE u.deleted=false", UserEntity.class).getResultList();
    }

    public void delete(Long id) {
        UserEntity reference = em.getReference(UserEntity.class, id);
        reference.setDeleted(true);
        em.merge(reference);
    }

    public String getRole(Long id) {
        return get(id).getRole();
    }

    public UserEntity addRole(Long id, String role) {
        UserEntity reference = em.getReference(UserEntity.class, id);
        reference.setRoles(role);
        em.merge(reference);
        return reference;
    }

    public UserEntity delRole(Long id, String role) {
        UserEntity reference = em.getReference(UserEntity.class, id);
        if (reference.getRole().equalsIgnoreCase(role)) {
            reference.setRoles("");
        }
        em.merge(reference);
        return reference;
    }


}
