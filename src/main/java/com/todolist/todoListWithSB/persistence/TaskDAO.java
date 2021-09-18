package com.todolist.todoListWithSB.persistence;


import com.todolist.todoListWithSB.model.Task;


import java.util.Collection;


public interface TaskDAO {

    long save(Task task);

    boolean setAssignee(Long id,String assignee);

    void delete(long id);

    void edit(long id, String text);

    void deleteAll();

    Collection<Task> getAll();

}
