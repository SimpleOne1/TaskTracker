package com.education.suites;

import com.education.model.User;
import com.education.model.UserAdjustment;
import com.education.model.UserTasks;
import com.education.suites.TestSuite;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserTestSuite extends TestSuite {

    private final User USER = new User(null, "Dan", "1@gmail.com", false);

    @Test
    public void createGetTest() throws Exception {
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
        createGetTest();
        MvcResult resultAfterSave = getMockMvc().perform(get("/users").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        User[] usersAfterSave = fromResponse(resultAfterSave, User[].class);
        User onlyUser = usersAfterSave[0];
        MvcResult singleGetResult = getMockMvc().perform(get("/users/" + onlyUser.getId()).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        UserTasks userTasks = fromResponse(singleGetResult, UserTasks.class);
        assertThat(userTasks.getUser()).usingRecursiveComparison().isEqualTo(onlyUser);
    }

    @Test
    public void singleGetDeleteTest() throws Exception {
        getByIdTest();
        MvcResult resultAfterSave = getMockMvc().perform(get("/users").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        User[] usersAfterSave = fromResponse(resultAfterSave, User[].class);
        User onlyUser = usersAfterSave[0];
        Long id = onlyUser.getId();
        onlyUser.setId(null);
        assertThat(onlyUser).usingRecursiveComparison().isEqualTo(USER);

        getMockMvc().perform(delete("/users/" + id).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        getMockMvc().perform(get("/users/" + id).accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

    }

    @Test
    public void editTest() throws Exception {
        getByIdTest();
        UserAdjustment userAdjustment = new UserAdjustment();
        userAdjustment.setName("afff");
        userAdjustment.setEmail("d1@gmail.com");
        userAdjustment.setTasks(null);
        String json = asJson(userAdjustment);
        MvcResult resultAfterSave = getMockMvc().perform(get("/users").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        User[] usersAfterSave = fromResponse(resultAfterSave, User[].class);
        User onlyUser = usersAfterSave[0];
        Long id = onlyUser.getId();
        getMockMvc().perform(post("/users/" + id).content(json).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        MvcResult singleGetResult = getMockMvc().perform(get("/users/" + id).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        UserTasks userTasks = fromResponse(singleGetResult, UserTasks.class);
        User user = userTasks.getUser();
        assertThat(userTasks.getTasks()).isNull();
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getName()).isEqualTo(userAdjustment.getName());
        assertThat(user.getEmail()).isEqualTo(userAdjustment.getEmail());
    }
}
