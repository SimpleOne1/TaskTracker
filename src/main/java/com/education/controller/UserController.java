package com.education.controller;

import com.education.model.User;
import com.education.model.UserAdjustment;
import com.education.model.UserTasks;
import com.education.services.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //works
    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("{id}")
    public UserTasks get(@PathVariable(value = "id") long id) {
        return userService.get(id);
    }

    @PostMapping
    public User save(@Valid @RequestBody User user) {
       return userService.saveUser(user);
    }

    @PostMapping("{id}")
    public void update(@PathVariable(value = "id") long id,@Valid @RequestBody UserAdjustment user) {
        userService.edit(id, user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable(value = "id") long id) {
        userService.delete(id);
    }

}
