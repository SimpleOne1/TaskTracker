package com.education.controller;

import com.education.repository.ProjectToCreate;
import com.education.repository.entity.ProjectEntity;
import com.education.repository.entity.UserEntity;
import com.education.services.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projects")
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProjectEntity> getAll() {
        return service.getAll();
    }
    @GetMapping("{id}")
    public ProjectEntity get(@PathVariable(value = "id") long id) {
        return service.get(id);
    }

    @PostMapping
    public ProjectEntity save(@RequestBody ProjectToCreate project) {
        return service.create(project);
    }
    @PostMapping("{id}")
    public void update(@PathVariable(value = "id") long id,@RequestBody ProjectToCreate project) {
        service.edit(id, project);
    }

   @PostMapping("/addteam/{id}")
    private  void addTeam(@PathVariable(value = "id") long id,@RequestBody Long teamId){
        service.addTeam(id,teamId);
   }
    @DeleteMapping("/addteam/{id}")
    private  void delTeam(@PathVariable(value = "id") long id,@RequestBody Long teamId){
        service.delTeam(id,teamId);
    }

}
