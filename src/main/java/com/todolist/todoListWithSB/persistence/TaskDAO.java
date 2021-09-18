package com.todolist.todoListWithSB.persistence;


import com.todolist.todoListWithSB.model.Task;
import org.springframework.stereotype.Repository;

import java.util.Collection;


public interface TaskDAO {

    Integer save(Task task);

    void delete(Integer id);

    void edit(Integer id, String text);

    void deleteAll();

    Collection<Task> getAll();

}
