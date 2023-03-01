package ua.com.vit.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.vit.exceptions.IndelibleEntityException;
import ua.com.vit.repository.entities.Course;
import ua.com.vit.service.CourseService;
import ua.com.vit.validators.CourseValidator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CourseRestController.class)
public class CourseRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CourseService serviceCourseMock;

    @MockBean
    CourseValidator validatorCourseMock;

    @Test
    public void should_returnIsOkStatus_when_controllerCallsShowAllMethod() throws Exception {

        this.mockMvc.perform(get("/rest/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_returnIsOkStatus_when_controllerCallsShowByIdMethod() throws Exception {

        String id = "1";

        this.mockMvc.perform(get("/rest/v1/courses/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_returnCreatedStatus_when_controllerCallsCreateMethod() throws Exception {

        Course course = new Course();
        course.setId(15);
        course.setCourseName("Course name");

        this.mockMvc.perform(post("/rest/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(course)))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_returnIsNoContentStatus_when_controllerCallsUpdateMethod() throws Exception {

        Course course = new Course();
        course.setId(15);
        course.setCourseName("Changed course name");

        this.mockMvc.perform(put("/rest/v1/courses/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(course)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_returnIsNoContentStatus_when_controllerCallsDeleteMethod() throws Exception {

        Course course = new Course();
        course.setId(15);
        course.setCourseName("Changed course name");

        this.mockMvc.perform(delete("/rest/v1/courses/" + course.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_returnIsBadRequestStatus_when_controllerCallsDeleteMethodWithInvalidData()
            throws Exception {

        int courseId = 1;

        Mockito.doThrow(new IndelibleEntityException()).when(serviceCourseMock).deleteById(courseId);

        this.mockMvc.perform(delete("/rest/v1/courses/" + courseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

}
