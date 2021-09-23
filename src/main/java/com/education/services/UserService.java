package com.education.services;

import com.education.model.Task;
import com.education.model.User;
import com.education.model.UserAdjustment;
import com.education.model.UserTasks;
import com.education.persistence.TaskDAO;
import com.education.persistence.UserDAO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final TaskDAO taskDAO;

    public UserService(UserDAO userDAO,TaskDAO taskDAO) {
        this.userDAO = userDAO;
        this.taskDAO=taskDAO;
    }

    public User saveUser(User user) {
        User byEmail = userDAO.getByEmail(user.getEmail());
        if (byEmail != null) {
            throw new IllegalArgumentException();
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
        if(user==null){
            throw new IllegalArgumentException();
        }
        user.setDeleted(true);
        userDAO.edit(id,user);
    }

    public UserTasks get(Long id) {
        UserTasks userTasks = new UserTasks();
        userTasks.setUser(userDAO.get(id));
        userTasks.setTasks(taskDAO.getByAssignee(id));
        return userTasks;
    }

    public void edit(Long id, UserAdjustment user) {
        User oldUser = userDAO.get(id);
        if (user.getEmail() != null) {
            User byEmail = userDAO.getByEmail(user.getEmail());
            if (byEmail != null) {
                if(Objects.equals(byEmail.getId(),id)){
                    throw new IllegalArgumentException("email duplicated");
                }
                throw new IllegalArgumentException("email is same");
            } else {
                oldUser.setEmail(user.getEmail());
            }
        }
        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }
        userDAO.edit(id, oldUser);
    }
}
