package com.education.persistence;

import com.education.model.CreateUserRequest;
import com.education.model.User;


import java.util.Collection;

public interface UserDAO {

    long save(User user);

    User edit(long id, User user);

    User get(long id);

    User getByEmail(String email);

    Collection<User> getAll();

//    User createUserFromRequest(CreateUserRequest request);
}
