package com.education.controller;

import com.education.model.UserTasks;
import com.education.services.TaskService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("users/{userId}/task")
public class UserTaskController {
    private final TaskService service;

    public UserTaskController(TaskService taskService) {
        this.service = taskService;
    }

    @PostMapping("{taskId}")
    public void setAssignee(@PathVariable(value = "taskId") long taskId, @PathVariable(value = "userId") long userId) {
        service.setAssignee(taskId, userId);
    }

    @GetMapping
    public UserTasks getAll(@PathVariable(value="userId")long id){
        return service.getUserTasks(id);
    }
}
