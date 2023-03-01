package ua.com.vit.controllers.ui;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.com.vit.repository.entities.Faculty;
import ua.com.vit.service.FacultyService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FacultyControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FacultyService serviceFaculty;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void should_getListOfFaculties_when_controllerCallsShowAllMethod() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/faculties"))
                .andExpect(status().isOk())
                .andExpect(view().name("faculties/showAll"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(model().attribute("faculties", serviceFaculty.getAll()))
                .andReturn();
    }

    @Test
    public void should_getOneFaculty_when_controllerCallsShowByIdMethod() throws Exception {

        int id = 1;

        this.mockMvc.perform(MockMvcRequestBuilders.get("/faculties/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("faculties/showById"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(model().attribute("faculty", serviceFaculty.getById(id)))
                .andReturn();
    }

    @Test
    public void should_showCreationView_when_controllerCallsCreateMethod() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/faculties/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("faculties/create"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void should_addNewFacultyToDatabase_when_controllerCallsAddToDBMethod() throws Exception {

        Faculty faculty = new Faculty();
        faculty.setFacultyName("Faculty name");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/faculties")
                        .param("facultyName", faculty.getFacultyName()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/faculties"))
                .andExpect(redirectedUrl("/faculties"))
                .andReturn();
    }

    @Test
    public void should_updateFacultyInDatabase_when_controllerCallsUpdateMethod() throws Exception {

        Faculty faculty = new Faculty();
        faculty.setId(2);
        faculty.setFacultyName("Changed faculty name");

        this.mockMvc.perform(MockMvcRequestBuilders.put("/faculties/{id}", faculty.getId())
                        .param("id", Integer.toString(faculty.getId()))
                        .param("facultyName", faculty.getFacultyName()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/faculties"))
                .andExpect(redirectedUrl("/faculties"))
                .andReturn();
    }

    @Test
    public void should_deleteFacultyFromDatabase_when_controllerCallsDeleteMethodForDeletableEntity()
            throws Exception {

        Faculty faculty = new Faculty();
        faculty.setFacultyName("Name of faculty");
        serviceFaculty.create(faculty);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/faculties/{id}", faculty.getId())
                        .param("facultyName", faculty.getFacultyName()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/faculties"))
                .andExpect(redirectedUrl("/faculties"))
                .andReturn();
    }

}
