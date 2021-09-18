package com.todolist.todoListWithSB.services;


import com.todolist.todoListWithSB.model.Task;
import com.todolist.todoListWithSB.persistence.TaskDAO;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TaskService {
    private long id =0;

    private final TaskDAO taskDAO;

    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public boolean saveTask(Task task){
        if(Long.valueOf(task.getId())==null){
            task.setId(++id);
        }

        if(task.getAssignee()!=null){

        }
        if(task.getDescription()==null || task.getTitle()==null){
            return false;
        }
        taskDAO.save(task);
        return true;
    }
    public void deleteTask(long id){
        taskDAO.delete(id);
    }
    public void deleteAll(){
        taskDAO.deleteAll();
    }
    public void editTask(long id,String text){
        taskDAO.edit(id,text);
    }
    public Collection<Task> getAll(){
        return taskDAO.getAll();
    }

}
