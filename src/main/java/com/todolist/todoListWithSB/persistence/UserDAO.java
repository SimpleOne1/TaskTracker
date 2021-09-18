package com.todolist.todoListWithSB.persistence;

import com.todolist.todoListWithSB.model.User;

import java.util.Collection;

public interface UserDAO {

    long save(User user);

    void edit(long id,User user);

    void delete(long id);

    User get(long id);

    Collection<User> getAll();
}
