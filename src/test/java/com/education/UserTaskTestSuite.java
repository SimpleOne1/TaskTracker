package com.education;

import com.education.model.Task;
import com.education.model.User;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserTaskTestSuite extends TestSuite{
    private static final Task TASK = new Task(1L,"do smth","do smth1",1000L,null);
    private final User USER = new User(null, "Dan", "1@gmail.com", false);

    @Test
    public void createGetTask() throws Exception{
        createUserTest();
        getMockMvc().perform(get("/users").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json = asJson(TASK);
        MvcResult result = getMockMvc().perform(post("/tasks").content(json).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Task response = fromResponse(result, Task.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(TASK);
        MvcResult resultAfterSave = getMockMvc().perform(get("/tasks").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Task[] tasksAfterSave = fromResponse(resultAfterSave, Task[].class);
        Task onlyTask = tasksAfterSave[0];
        assertThat(onlyTask).usingRecursiveComparison().isEqualTo(TASK);
    }

    @Test
    public void createUserTest() throws Exception {
        getMockMvc().perform(get("/users").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(("[]")));
        String json = asJson(USER);
        MvcResult result = getMockMvc().perform(post("/users").content(json).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        User response = fromResponse(result, User.class);
        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo(USER.getName());
        MvcResult resultAfterSave = getMockMvc().perform(get("/users").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        User[] usersAfterSave = fromResponse(resultAfterSave, User[].class);
        assertThat(usersAfterSave).hasSize(1);
        User onlyUser = usersAfterSave[0];
        assertThat(onlyUser.getId()).isNotNull();
        assertThat(onlyUser.getName()).isEqualTo(USER.getName());
        assertThat(onlyUser.getEmail()).isEqualTo(USER.getEmail());
        assertThat(onlyUser.isDeleted()).isFalse();
    }

    @Test
    public void setAssigneeTest() throws Exception{
        createGetTask();
        MvcResult resultAfterSave = getMockMvc().perform(get("/tasks").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Task[] tasksAfterSave = fromResponse(resultAfterSave, Task[].class);
        Task onlyTask = tasksAfterSave[0];
        Long taskId = onlyTask.getId();
        String json = asJson(1000L);
        getMockMvc().perform(post("/users/{userId}/task/"+taskId,1000L).content(json).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        MvcResult resultAfterSetting = getMockMvc().perform(get("/tasks").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Task[] tasksAfterSetting = fromResponse(resultAfterSetting, Task[].class);
        Task taskAfterSetting = tasksAfterSetting[0];
        assertThat(taskAfterSetting.getAssignee()).isEqualTo(1000L);
    }

}
