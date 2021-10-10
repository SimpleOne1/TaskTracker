package com.education;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationMain.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class TestSuite {

    @Autowired
    private MockMvc mockMvc;

    public static String asJson(Object object) throws Exception {
        return new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(object);
    }

    public static <T> T fromResponse(MvcResult mvcResult, Class<T> type) throws Exception {
        return new ObjectMapper().readerFor(type).readValue(mvcResult.getResponse().getContentAsString());
    }

    public MockMvc getMockMvc() {
        return mockMvc;
    }
}
