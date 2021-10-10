package com.education.services;


import com.education.model.*;
import com.education.persistence.UserDAO;
import com.education.persistence.TaskDAO;
import com.education.services.exceptions.TaskIllegalArgumentException;
import com.education.services.exceptions.TaskNotFoundException;
import com.education.services.exceptions.UserDeletedException;
import com.education.services.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    private final TaskDAO taskDAO;
    private final UserDAO userDAO;

    public TaskService(TaskDAO taskDAO, UserDAO userDAO) {
        this.taskDAO = taskDAO;
        this.userDAO = userDAO;
    }

    public Task saveTask(Task task) {
        if (task.getReporter() == null) {
            throw new TaskIllegalArgumentException();
        }
        Long reporterId = task.getReporter();
        if (userDAO.get(reporterId).isDeleted()) {
            throw new UserDeletedException(reporterId);
        }
        if (task.getAssignee() != null && userDAO.get(task.getAssignee()).isDeleted()) {
            throw new UserDeletedException(task.getAssignee());
        }
        if (task.getDescription() == null || task.getTitle() == null) {
            throw new TaskIllegalArgumentException();
        }
        long savedId = taskDAO.save(task);
        task.setId(savedId);
        return task;
    }

    public void setAssignee(Long taskId, Long assigneeId) {
        if (assigneeId != null && userDAO.get(assigneeId).isDeleted()) {
            throw new UserDeletedException(assigneeId);
        }
        taskDAO.setAssignee(taskId, assigneeId);
    }

    public void editTask(long id, TaskAdjustment taskAdjustment) {
        Task oldTask = taskDAO.get(id);
        if (oldTask == null) {
            throw new TaskNotFoundException(id);
        }
        if (taskAdjustment.getDescription() != null) {
            oldTask.setDescription(taskAdjustment.getDescription());
        }
        if (taskAdjustment.getTitle() != null) {
            oldTask.setTitle(taskAdjustment.getTitle());
        }
        taskDAO.edit(id, oldTask);
    }

    public Collection<Task> getAll() {
        return taskDAO.getAll();
    }

    public UserTasks getUserTasks(long userId) {
        User user = userDAO.get(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        List<Task> tasks = taskDAO.getByAssignee(userId);
        UserTasks userTasks = new UserTasks();
        userTasks.setUser(user);
        userTasks.setTasks(tasks);
        return userTasks;
    }

    public Task get(long id) {
        Task task = taskDAO.get(id);
        if (task == null) {
            throw new TaskNotFoundException(id);
        }
        return task;
    }


}
