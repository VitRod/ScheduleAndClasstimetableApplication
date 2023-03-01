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
import ua.com.vit.repository.entities.Course;
import ua.com.vit.service.CourseService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CourseControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CourseService serviceCourse;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void should_getListOfCourses_when_controllerCallsShowAllMethod() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/courses"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/showAll"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(model().attribute("courses", serviceCourse.getAll()))
                .andReturn();
    }

    @Test
    public void should_getOneCourse_when_controllerCallsShowByIdMethod() throws Exception {

        int id = 8;

        this.mockMvc.perform(MockMvcRequestBuilders.get("/courses/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/showById"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(model().attribute("course", serviceCourse.getById(id)))
                .andReturn();
    }

    @Test
    public void should_showCreationView_when_controllerCallsCreateMethod() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/courses/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses/create"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void should_addNewCourseToDatabase_when_controllerCallsAddToDBMethod() throws Exception {

        Course course = new Course();
        course.setCourseName("Course's name");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/courses")
                        .param("courseName", course.getCourseName()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/courses"))
                .andExpect(redirectedUrl("/courses"))
                .andReturn();
    }

    @Test
    public void should_updateCourseInDatabase_when_controllerCallsUpdateMethod() throws Exception {

        Course course = new Course();
        course.setId(3);
        course.setCourseName("Another course's name");

        this.mockMvc.perform(MockMvcRequestBuilders.put("/courses/{id}", course.getId())
                        .param("id", Integer.toString(course.getId()))
                        .param("courseName", course.getCourseName()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/courses"))
                .andExpect(redirectedUrl("/courses"))
                .andReturn();
    }

    @Test
    public void should_deleteCourseFromDatabase_when_controllerCallsDeleteMethodForDeletableEntity()
            throws Exception {

        Course course = new Course();
        course.setCourseName("And another course's name");
        serviceCourse.create(course);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/courses/{id}", course.getId())
                        .param("courseName", course.getCourseName()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/courses"))
                .andExpect(redirectedUrl("/courses"))
                .andReturn();
    }

}
