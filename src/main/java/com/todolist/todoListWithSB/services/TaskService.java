package com.todolist.todoListWithSB.services;


import com.todolist.todoListWithSB.model.Task;
import com.todolist.todoListWithSB.persistence.TaskDAO;
import com.todolist.todoListWithSB.persistence.UserDAO;
import org.springframework.stereotype.Service;

import java.util.Collection;
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

    public boolean saveTask(Task task, long reporterId) {
        if (task.getId() == 0) {
            task.setId(atomicLong.incrementAndGet());
        }
        if (task.getReporter() == null) {
            task.setReporter(reporterId);
        } else if (userDAO.get(reporterId).isDeleted()) {
            return false;
        }
        if (task.getAssignee() != null && userDAO.get(task.getAssignee()).isDeleted()) {
            return false;
        }
        if (task.getDescription() == null || task.getTitle() == null) {
            return false;
        }
        taskDAO.save(task);
        return true;
    }

    public boolean setAssignee(Long taskId, Long assigneeId) {
        if (assigneeId == null) {
            return false;
        }
        taskDAO.setAssignee(taskId, assigneeId);
        return true;
    }

    public void deleteTask(long id) {
        taskDAO.delete(id);
    }

    public void deleteAll() {
        taskDAO.deleteAll();
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

    public Task get(long id) {
        return taskDAO.get(id);
    }

}
