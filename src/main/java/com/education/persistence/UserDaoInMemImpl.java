package com.education.persistence;

import com.education.model.CreateUserRequest;
import com.education.model.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDaoInMemImpl implements UserDAO {

    private long id = 1000;
    private final Map<Long, User> userStorage = new HashMap<>();
    private final Map<String, User> userByEmail = new HashMap<>();


    @Override
    public long save(User user) {
        if (user.getId() == 0) {
            user.setId(id++);
        }
        userStorage.put(user.getId(), user);
        userByEmail.put(user.getEmail(), user);
        return user.getId();
    }

    @Override
    public User edit(long id, User user) {
        User old = userStorage.put(id, user);
        if (old != null) {
            userByEmail.remove(old.getEmail());
            userByEmail.put(user.getEmail(), user);
        }
        return old;
    }

    @Override
    public User createUserFromRequest(CreateUserRequest request) {
        User user = new User(request.getId(), request.getName(), request.getEmail(), request.isDeleted());
        userStorage.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(long id) {
        return userStorage.get(id);
    }

    @Override
    public Collection<User> getAll() {
        return userStorage.values();
    }

    @Override
    public User getByEmail(String email) {
        return userByEmail.get(email);
    }
}
