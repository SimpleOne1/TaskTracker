package com.education.controller;

import com.education.repository.TeamCreating;
import com.education.repository.entity.ProjectEntity;
import com.education.repository.entity.TeamEntity;
import com.education.services.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("teams")
public class TeamController {
    private final TeamService service;

    public TeamController(TeamService teamService) {
        this.service = teamService;
    }

    @GetMapping
    public List<TeamEntity> getAll() {
        return service.getAll();
    }
    @GetMapping("{id}")
    public TeamEntity get(@PathVariable(value = "id") long id) {
        return service.get(id);
    }

    @PostMapping
    public TeamEntity create(@RequestBody TeamCreating team){
        return service.create(team);
    }

    @PostMapping("{id}")
    public TeamEntity edit(@PathVariable(value="id")long id,@RequestBody TeamCreating team){
        return service.edit(id,team);
    }

    @PostMapping("{teamId}/{userId}")
    public TeamEntity addUser(@PathVariable(value="teamId")long teamId,@PathVariable(value="userId")long userId){
        return service.addUser(teamId,userId);
    }
}
