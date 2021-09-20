package com.todolist.todoListWithSB.persistence;

import com.todolist.todoListWithSB.model.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDaoInMemImpl implements UserDAO {

    private final Map<Long, User> userStorage = new HashMap<>();

    @Override
    public long save(User user) {
        userStorage.put(user.getId(), user);
        return user.getId();
    }

    @Override
    public void edit(long id, User user) {
        userStorage.put(id, user);
    }

    @Override
    public void delete(long id) {
        userStorage.get(id).setDeleted(true);
    }

    @Override
    public User get(long id) {
        return userStorage.get(id);
    }

    @Override
    public Collection<User> getAll() {
        return userStorage.values();
    }
}
