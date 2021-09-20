package com.todolist.todoListWithSB.persistence;

import com.todolist.todoListWithSB.model.Task;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TaskDaoInMemImpl implements TaskDAO {


    private final Map<Long, Task> taskStorage = new HashMap<>();

    public TaskDaoInMemImpl() {
    }

    @Override
    public void setAssignee(Long taskId, Long assigneeId) {
        taskStorage.get(taskId).setAssignee(assigneeId);
    }

    @Override
    public long save(Task task) {
        taskStorage.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public void delete(long id) {
        taskStorage.remove(id);
    }

    @Override
    public void deleteAll() {
        taskStorage.clear();
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
}
