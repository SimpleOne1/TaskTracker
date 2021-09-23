package com.education.persistence;


import com.education.model.Task;


import java.util.Collection;
import java.util.List;


public interface TaskDAO {

    long save(Task task);

    void setAssignee(Long id, Long assignee);

    void edit(long id, Task task);

    Collection<Task> getAll();

    Task get(long id);

    List<Task> getByAssignee(long userId);

}
