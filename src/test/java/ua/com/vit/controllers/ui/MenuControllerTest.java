package ua.com.vit.controllers.ui;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
public class MenuControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new MenuController()).build();
    }

    @Test
    public void should_returnMenuPage_when_controllerStarts() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("menu"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
