package com.education.suites;

import com.education.model.Task;
import com.education.model.TaskAdjustment;
import com.education.model.User;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TaskTestSuite extends TestSuite {
    private User user;
    private Long taskId;

    @BeforeEach
    public void setUp() {
        user = createUser();
    }

    @Test
    public void createGetTask() throws Exception {
        getMockMvc().perform(get("/users").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Task task = new Task(null, "do smth", "do smth1", user.getId(), null);
        String json = asJson(task);
        MvcResult result = getMockMvc().perform(post("/tasks").content(json).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Task response = fromResponse(result, Task.class);

        assertThat(response.getId()).isNotNull();
        task.setId(response.getId());
        assertThat(response).usingRecursiveComparison().isEqualTo(task);
        taskId = response.getId();
        MvcResult resultAfterSave = getMockMvc().perform(get("/tasks").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Task[] tasksAfterSave = fromResponse(resultAfterSave, Task[].class);
        Task onlyTask = tasksAfterSave[0];
        assertThat(onlyTask).usingRecursiveComparison().isEqualTo(task);
    }


    @Test
    public void getByIdTest() throws Exception {
        createGetTask();
        MvcResult resultAfterSave = getMockMvc().perform(get("/tasks").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Task[] tasksAfterSave = fromResponse(resultAfterSave, Task[].class);
        Task onlyTask = tasksAfterSave[0];
        Task expected = new Task(taskId, "do smth", "do smth1", user.getId(), null);
        assertThat(onlyTask).usingRecursiveComparison().isEqualTo(expected);
        MvcResult singleGetResult = getMockMvc().perform(get("/tasks/" + onlyTask.getId()).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Task task = fromResponse(singleGetResult, Task.class);
        assertThat(task).usingRecursiveComparison().isEqualTo(onlyTask);
    }

    @Test
    public void editTask() throws Exception {
        createGetTask();
        String json = asJson(new TaskAdjustment("name", "description"));
        MvcResult resultAfterSave = getMockMvc().perform(get("/tasks").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Task[] tasksAfterSave = fromResponse(resultAfterSave, Task[].class);
        Task onlyTask = tasksAfterSave[0];
        Long id = onlyTask.getId();
        getMockMvc().perform(post("/tasks/" + taskId).content(json).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        MvcResult singleGetResult = getMockMvc().perform(get("/tasks/" + id).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Task task = fromResponse(singleGetResult, Task.class);
        assertThat(task.getDescription()).isEqualTo("description");
        assertThat(task.getTitle()).isEqualTo("name");
        assertThat(task.getId()).isEqualTo(taskId);
    }

    @Test
    public void setAssigneeTest() throws Exception {
        createGetTask();
        MvcResult resultAfterSave = getMockMvc().perform(get("/tasks").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Task[] tasksAfterSave = fromResponse(resultAfterSave, Task[].class);
        Task onlyTask = tasksAfterSave[0];
        Long taskId = onlyTask.getId();
        String json = asJson(1000L);
        getMockMvc().perform(post("/users/{userId}/task/" + taskId, 1000L).content(json).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
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
