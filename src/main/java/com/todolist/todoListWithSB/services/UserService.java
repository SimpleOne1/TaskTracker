package com.todolist.todoListWithSB.services;

import com.todolist.todoListWithSB.model.User;
import com.todolist.todoListWithSB.persistence.UserDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private long id = 1000;

    private Set<String> uniqueEmails = new HashSet<>();

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean saveUser(User user) {
        if (Long.valueOf(user.getId()).equals(null)) {
            user.setId(id++);
        }
        if (uniqueEmails.contains(user.getEmail())) {
            return false;
        }
        uniqueEmails.add(user.getEmail());
        userDAO.save(user);
        return true;
    }

    public ArrayList<User> getAll() {
        ArrayList<User> list = new ArrayList<>();
        for (User user : userDAO.getAll()) {
            if (user.isDeleted() == false) {
                list.add(user);
            }
        }
        return list;
    }

    public void delete(Long id) {
        userDAO.delete(id);
    }

    public User get(Long id) {
        return userDAO.get(id);
    }

    public void edit(Long id, User user) {
        User oldUser = userDAO.get(id);
        if (user.getEmail() != null && !uniqueEmails.contains(user.getEmail())) {
            if (oldUser.getEmail() != null) {
                uniqueEmails.remove(oldUser.getEmail());
            }
            oldUser.setEmail(user.getEmail());
        }
        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }
        if (user.getTasks() != null) {
            oldUser.setTasks(user.getTasks());
        }
        userDAO.edit(id, oldUser);
    }
}
