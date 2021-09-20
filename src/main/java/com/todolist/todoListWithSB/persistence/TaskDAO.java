package com.todolist.todoListWithSB.persistence;


import com.todolist.todoListWithSB.model.Task;


import java.util.Collection;


public interface TaskDAO {

    long save(Task task);

    void setAssignee(Long id, Long assignee);

    void delete(long id);

    void edit(long id, Task task);

    void deleteAll();

    Collection<Task> getAll();

    Task get(long id);

}
