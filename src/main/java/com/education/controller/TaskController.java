package com.education.controller;

import com.education.model.Task;
import com.education.model.TaskAdjustment;
import com.education.services.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService taskService) {
        this.service = taskService;
    }

    @GetMapping
    public Collection<Task> getAll() {
        System.out.println(service.getAll().toString());
        return service.getAll();
    }

    @GetMapping("{taskId}")
    public Task getTask(@PathVariable(value = "taskId") long id) {
        return service.get(id);
    }

    @PostMapping
    public Task save(@Valid @RequestBody Task task) {
        return service.saveTask(task);
    }

    @PostMapping("{taskId}")
    public void update(@PathVariable(value = "taskId") long taskId, @RequestBody TaskAdjustment task) {
        service.editTask(taskId, task);
    }

}
