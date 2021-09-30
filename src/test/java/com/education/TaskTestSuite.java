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


public class TaskTestSuite extends TestSuite {
    private final UserTestSuite userTestSuite = new UserTestSuite();
    private static final Task TASK = new Task(1L,"do smth","do smth1",1000L,null);
    private static final Task SECOND_TASK = new Task(2L,"do","do",1000L,null);
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
    public void getByIdTest() throws Exception {
        createGetTask();
        MvcResult resultAfterSave = getMockMvc().perform(get("/tasks").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Task[] tasksAfterSave = fromResponse(resultAfterSave, Task[].class);
        Task onlyTask = tasksAfterSave[0];
        assertThat(onlyTask).usingRecursiveComparison().isEqualTo(TASK);
        MvcResult singleGetResult = getMockMvc().perform(get("/tasks/" + onlyTask.getId()).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Task task = fromResponse(singleGetResult, Task.class);
        assertThat(task).usingRecursiveComparison().isEqualTo(onlyTask);
    }

    @Test
    public void editTask() throws Exception{
        createGetTask();
        String json = asJson(SECOND_TASK);
        MvcResult resultAfterSave = getMockMvc().perform(get("/tasks").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Task[] tasksAfterSave = fromResponse(resultAfterSave, Task[].class);
        Task onlyTask = tasksAfterSave[0];
        Long id = onlyTask.getId();
        getMockMvc().perform(post("/tasks/" + id).content(json).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        MvcResult singleGetResult = getMockMvc().perform(get("/tasks/" + id).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Task task = fromResponse(singleGetResult, Task.class);
        assertThat(task.getDescription()).isEqualTo(SECOND_TASK.getDescription());
        assertThat(task.getTitle()).isEqualTo(SECOND_TASK.getTitle());
        assertThat(task.getId()).isEqualTo(TASK.getId());
    }



}
