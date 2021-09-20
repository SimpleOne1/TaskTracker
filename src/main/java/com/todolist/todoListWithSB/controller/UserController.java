package com.todolist.todoListWithSB.controller;

import com.todolist.todoListWithSB.model.User;
import com.todolist.todoListWithSB.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //works
    @GetMapping
    public ArrayList<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("{id}")
    public User get(@PathVariable(value = "id") long id) {
        return userService.get(id);
    }

    @PostMapping
    public void save(@RequestBody User user) {
        userService.saveUser(user);
    }

    @PostMapping("{id}")
    public void update(@PathVariable(value = "id") long id, @RequestBody User user) {
        userService.edit(id, user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable(value = "id") long id) {
        userService.delete(id);
    }

}
