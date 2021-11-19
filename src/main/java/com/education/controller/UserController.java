package com.education.controller;


import com.education.repository.ChangeableUser;
import com.education.repository.entity.UserEntity;
import com.education.services.UserService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    @GetMapping
    public List<UserEntity> getAll() {
        return service.getAll();
    }
    @GetMapping("{id}")
    public UserEntity get(@PathVariable(value = "id") long id) {
        return service.get(id);
    }

    @PostMapping
    public UserEntity save(@RequestBody UserEntity user) {
        return service.save(user);
    }
    @PostMapping("{id}")
    public void update(@PathVariable(value = "id") long id,@RequestBody ChangeableUser user) {
        service.edit(id, user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable(value = "id") long id) {
        service.delete(id);
    }
    @PostMapping("{id}/role")
    public UserEntity addRole(@PathVariable(value = "id") long id,@RequestBody String role) {
        return service.addRole(id, role);
    }
    @DeleteMapping("{id}/role")
    public UserEntity delRole(@PathVariable(value = "id") long id,@RequestBody String role) {
        return service.delRole(id, role);
    }
}
