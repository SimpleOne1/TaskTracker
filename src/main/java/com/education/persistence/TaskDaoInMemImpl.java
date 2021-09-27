package com.education.persistence;

import com.education.model.Task;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TaskDaoInMemImpl implements TaskDAO {

    private final AtomicLong counter = new AtomicLong(0);

    private final Map<Long, Task> taskStorage = new HashMap<>();
    private final Map<Long, List<Task>> taskStorageByAssignee = new HashMap<>();


    @Override
    public void setAssignee(Long taskId, Long assigneeId) {
        taskStorage.get(taskId).setAssignee(assigneeId);
        if (taskStorageByAssignee.get(assigneeId) != null) {
            taskStorageByAssignee.get(assigneeId).add(taskStorage.get(taskId));
        } else {
            List<Task> assigneeTasks = new ArrayList<>();
            assigneeTasks.add(taskStorage.get(taskId));
            taskStorageByAssignee.put(assigneeId, assigneeTasks);
        }
    }

    @Override
    public long save(Task task) {
        task.setId(counter.incrementAndGet());
        if (task.getAssignee() != null) {
            long assigneeId = task.getAssignee();
            if (taskStorageByAssignee.get(assigneeId) != null && !taskStorageByAssignee.get(assigneeId).contains(task)) {
                taskStorageByAssignee.get(task.getAssignee()).add(task);
            } else if (taskStorageByAssignee.get(assigneeId) == null) {
                List<Task> assigneeTasks = new ArrayList<>();
                assigneeTasks.add(task);
                taskStorageByAssignee.put(task.getAssignee(), assigneeTasks);
            }
        }
        taskStorage.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public void edit(long id, Task task) {
        taskStorage.put(id, task);
    }

    @Override
    public Collection<Task> getAll() {
        return taskStorage.values();
    }

    @Override
    public Task get(long id) {
        return taskStorage.get(id);
    }

    @Override
    public List<Task> getByAssignee(long assigneeId) {
        return taskStorageByAssignee.get(assigneeId);
    }
}
