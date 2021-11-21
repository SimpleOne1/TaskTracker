package com.education.controller;
import com.education.repository.ChangeableTask;
import com.education.repository.entity.TaskEntity;
import com.education.services.TaskService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/{projectId}/{reporterId}/tasks")
public class UserTaskController {
    private final TaskService service;

    public UserTaskController(TaskService taskService) {
        this.service = taskService;
    }

    @PostMapping
    public TaskEntity create(@PathVariable(value = "reporterId") long reporterId,
                             @PathVariable(value = "projectId") long projectId,
                             @RequestBody TaskEntity taskEntity) {
        return service.save(projectId, reporterId, taskEntity);
    }

    @PostMapping("{taskId}/{assigneeId}")
    public TaskEntity setAssignee(@PathVariable(value = "taskId") long taskId,
                                  @PathVariable(value = "assigneeId") long assigneeId) {
        return service.setAssignee(taskId, assigneeId);
    }

    @PostMapping("{taskId}")
    public void update(@PathVariable(value = "taskId") long taskId,
                       @RequestBody ChangeableTask task) {
        service.edit(taskId, task);
    }
}
