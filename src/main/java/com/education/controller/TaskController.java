package com.education.controller;
import com.education.repository.entity.TaskEntity;
import com.education.services.TaskService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService taskService) {
        this.service = taskService;
    }

    @GetMapping
    public List<TaskEntity> getAll() {
        return service.getAll();
    }

    @GetMapping("{taskId}")
    public TaskEntity getTask(@PathVariable(value = "taskId") long id) {
        return service.get(id);
    }

}
