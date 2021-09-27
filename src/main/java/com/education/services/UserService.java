package com.education.services;

import com.education.model.*;
import com.education.persistence.TaskDAO;
import com.education.persistence.UserDAO;
import com.education.services.exceptions.TaskNotFoundException;
import com.education.services.exceptions.UniqueEmailException;
import com.education.services.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final TaskDAO taskDAO;

    public UserService(UserDAO userDAO, TaskDAO taskDAO) {
        this.userDAO = userDAO;
        this.taskDAO = taskDAO;
    }

    public User saveUser(User user) {
        User byEmail = userDAO.getByEmail(user.getEmail());
        if (byEmail != null) {
            throw new UniqueEmailException();
        }
        long id = userDAO.save(user);
        user.setId(id);
        return user;
    }

    public List<User> getAll() {
        ArrayList<User> list = new ArrayList<>();
        for (User user : userDAO.getAll()) {
            if (!user.isDeleted()) {
                list.add(user);
            }
        }
        return list;
    }

    public void delete(Long id) {
        User user = userDAO.get(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        user.setDeleted(true);
        userDAO.edit(id, user);
    }

    public UserTasks get(Long id) {
        UserTasks userTasks = new UserTasks();
        User user = userDAO.get(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        userTasks.setUser(user);
        List<Task> tasks = taskDAO.getByAssignee(id);
        if (tasks == null) {
            throw new TaskNotFoundException(id);
        }
        userTasks.setTasks(tasks);
        return userTasks;
    }

    public void edit(Long id, UserAdjustment user) {
        User oldUser = userDAO.get(id);
        if (oldUser == null) {
            throw new UserNotFoundException(id);
        }
        if (user.getEmail() != null) {
            User byEmail = userDAO.getByEmail(user.getEmail());
            if (byEmail != null) {
                if (Objects.equals(byEmail.getId(), id)) {
                    throw new UniqueEmailException("email duplicated");
                }
                throw new UniqueEmailException("email is same");
            } else {
                oldUser.setEmail(user.getEmail());
            }
        }
        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }
        userDAO.edit(id, oldUser);
    }

    public User createFromRequest(CreateUserRequest request) {
        ;
        return userDAO.createUserFromRequest(request);
    }
}
