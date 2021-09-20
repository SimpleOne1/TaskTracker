package com.todolist.todoListWithSB.controller;

import com.todolist.todoListWithSB.model.Task;
import com.todolist.todoListWithSB.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("users/{userId}/task")
public class UserTaskController {
    private final TaskService service;

    public UserTaskController(TaskService taskService) {
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
    public void save(@RequestBody Task task, @PathVariable(value = "userId") long id) {
        service.saveTask(task, id);
    }

    @PostMapping("{taskId}/{assigneeId}")
    public void setAssignee(@PathVariable(value = "taskId") long taskId, @PathVariable(value = "assigneeId") long assigneeId) {
        service.setAssignee(taskId, assigneeId);
    }

    @PostMapping ("{taskId}")
    public void update(@PathVariable(value="taskId")long taskId, @RequestBody Task task) {
        service.editTask(taskId, task);
    }

}
