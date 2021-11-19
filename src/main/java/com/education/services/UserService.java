package com.education.services;

import com.education.repository.ChangeableUser;
import com.education.repository.entity.UserEntity;
import com.education.repository.repo.UserRepository;
import com.education.services.exceptions.UserDeletedException;
import com.education.services.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserEntity get(Long id) {
        UserEntity user = repository.get(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        if (user.isDeleted()) {
            throw new UserDeletedException(id);
        }
        return repository.get(id);
    }

    public List<UserEntity> getAll() {
        return repository.getAll();
    }

    public UserEntity save(UserEntity userEntity) {
        return repository.save(userEntity);
    }

    public UserEntity edit(Long id, ChangeableUser user) {
        UserEntity entity = repository.get(id);
        if(entity==null){
            throw new UserNotFoundException(id);
        }
        if(user.getName()!=null){
            entity.setName(user.getName());
        }
        if(user.getEmail()!=null){
            entity.setEmail(user.getEmail());
        }
        if(user.getPassword()!=null){
            entity.setPassword(user.getPassword());
        }
        return repository.save(entity);
    }

    public void delete(Long id) {
        UserEntity user = repository.get(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        repository.delete(id);
    }

    public UserEntity addRole(Long id, String role) {
        UserEntity user = repository.get(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return repository.addRole(id, role);
    }

    public UserEntity delRole(Long id, String role) {
        UserEntity user = repository.get(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return repository.delRole(id,role);
    }
}
