package com.todolist.todoListWithSB.services;


import com.todolist.todoListWithSB.model.Task;
import com.todolist.todoListWithSB.persistence.TaskDAO;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TaskService {
    private int id =0;

    private final TaskDAO taskDAO;

    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public void saveTask(Task task){
        if(task.getId()==null){
            task.setId(++id);
        }
        taskDAO.save(task);
    }
    public void deleteTask(Integer id){
        taskDAO.delete(id);
    }
    public void deleteAll(){
        taskDAO.deleteAll();
    }
    public void editTask(Integer id,String text){
        taskDAO.edit(id,text);
    }
    public Collection<Task> getAll(){
        return taskDAO.getAll();
    }

}
