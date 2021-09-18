package com.todolist.todoListWithSB.controller;

import com.todolist.todoListWithSB.model.Task;
import com.todolist.todoListWithSB.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("todo")
public class taskController {
    private final TaskService service;

    public taskController(TaskService taskService) {
        this.service = taskService;
    }

    @GetMapping
    public Collection<Task> getAll(){
        return service.getAll();
    }

    @PostMapping
    public void save(@RequestBody Task task){
        service.saveTask(task);
    }

    @DeleteMapping({"{id}"})
    public void delete(@PathVariable(value = "id")int id){
        service.deleteTask(id);
    }

    @DeleteMapping
    public void deleteAll(){
        service.deleteAll();
    }

    public void update(int id,String text){
        service.editTask(id,text);
    }

}
