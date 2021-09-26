package com.education;

import com.education.model.Task;
import com.education.model.User;
import com.education.persistence.TaskDAO;
import com.education.persistence.TaskDaoInMemImpl;
import com.education.persistence.UserDAO;
import com.education.persistence.UserDaoInMemImpl;
import com.education.services.TaskService;

import com.education.services.exceptions.TaskIllegalArgumentException;
import com.education.services.exceptions.UniqueEmailException;
import com.education.services.exceptions.UserDeletedException;
import com.education.services.exceptions.UserNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TaskServiceTest {
    private final UserDAO userDAO = mock(UserDaoInMemImpl.class);
    private final TaskDAO taskDAO = mock(TaskDaoInMemImpl.class);
    private static final Long USER_ID = 10L;
    private static final User USER = new User(2L,"Dan","1@gmail.com",false,null);
    private static final User ASSIGNEE_USER = new User(30L,"John","john@gmail.com",true,null);
    private static final Task TASK = new Task(20L,"do smth","do smth1",2L,30L);
    private final TaskService service = new TaskService(taskDAO,userDAO);


    @Test
    public void testTaskSave_DeletedReporter(){
        when(userDAO.get(anyLong())).thenReturn(ASSIGNEE_USER);
        assertThrows(UserDeletedException.class,()->{service.saveTask(TASK);});
        verify(userDAO,times(1)).get(anyLong());

    }

    @Test
    public void testTaskSave_DeletedAssignee(){
        when(userDAO.get(anyLong())).thenReturn(USER,ASSIGNEE_USER);
        assertThrows(UserDeletedException.class,()->{service.saveTask(TASK);});
        verify(userDAO,times(2)).get(anyLong());
    }

    @Test
    public void testTaskSave(){
        when(userDAO.get(anyLong())).thenReturn(USER);
        when(taskDAO.save(any(Task.class))).thenReturn(20L);
        Task task = service.saveTask(TASK);
        assertThat(TASK).isEqualTo(task);
        verify(taskDAO).save(any(Task.class));
        verify(userDAO,times(2)).get(anyLong());
        verify(taskDAO,times(1)).save(any(Task.class));
    }

    @Test
    public void testGetUserTasks_UserNotFound(){
        when(userDAO.get(anyLong())).thenReturn(null);
        assertThrows(UserNotFoundException.class,()->{service.getUserTasks(USER_ID);});
        verify(userDAO,times(1)).get(anyLong());
    }

    @Test
    public void testGetUserTasks(){
        when(userDAO.get(anyLong())).thenReturn(new User(USER_ID,"diana","diana@gmail.com",false,null));
        when(taskDAO.getByAssignee(anyLong())).thenReturn(null);


    }


}
