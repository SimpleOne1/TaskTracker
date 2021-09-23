package com.education.services;


import com.education.model.User;
import com.education.model.UserTasks;
import com.education.persistence.UserDAO;
import com.education.model.Task;
import com.education.persistence.TaskDAO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    private final AtomicLong atomicLong = new AtomicLong(0);

    private final TaskDAO taskDAO;
    private final UserDAO userDAO;

    public TaskService(TaskDAO taskDAO, UserDAO userDAO) {
        this.taskDAO = taskDAO;
        this.userDAO = userDAO;
    }

    public Task saveTask(Task task) {
        if (task.getReporter() == null) {
            throw new IllegalArgumentException();
        }
        Long reporterId = task.getReporter();
        if (userDAO.get(reporterId).isDeleted()) {
            throw new IllegalArgumentException();
        }
        if (task.getAssignee() != null && userDAO.get(task.getAssignee()).isDeleted()) {
            throw new IllegalArgumentException();
        }
        if (task.getDescription() == null || task.getTitle() == null) {
            throw new IllegalArgumentException();
        }
        long savedId = taskDAO.save(task);
        task.setId(savedId);
        return task;
    }

    public void setAssignee(Long taskId, Long assigneeId) {
        if (assigneeId != null && userDAO.get(assigneeId).isDeleted()) {
            throw new IllegalArgumentException();
        }
        taskDAO.setAssignee(taskId, assigneeId);
    }

    public void editTask(long id, Task task) {
        task.setId(id);
        Task oldTask = taskDAO.get(id);
        if (task.getReporter() != null) {
            oldTask.setReporter(task.getReporter());
        }
        if (task.getDescription() != null) {
            oldTask.setDescription(task.getDescription());
        }
        if (task.getTitle() != null) {
            oldTask.setTitle(task.getTitle());
        }
        taskDAO.edit(id, oldTask);
    }

    public Collection<Task> getAll() {
        return taskDAO.getAll();
    }

    public UserTasks getUserTasks(long userId){
        User user = userDAO.get(userId);
        List<Task> tasks = taskDAO.getByAssignee(userId);
        UserTasks userTasks = new UserTasks();
        userTasks.setUser(user);
        userTasks.setTasks(tasks);
        return userTasks;
    }

    public Task get(long id) {
        return taskDAO.get(id);
    }

}
