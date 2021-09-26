package com.education.persistence;

import com.education.model.User;


import java.util.Collection;

public interface UserDAO {

    long save(User user);

    void edit(long id, User user);

    User get(long id);

    User getByEmail(String email);

    Collection<User> getAll();
}
