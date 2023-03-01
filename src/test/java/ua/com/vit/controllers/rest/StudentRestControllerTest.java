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
import ua.com.vit.domain.dto.StudentDto;
import ua.com.vit.service.StudentService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StudentRestController.class)
public class StudentRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentService serviceStudentMock;

    @Test
    public void should_returnIsOkStatus_when_controllerCallsShowAllMethod() throws Exception {

        this.mockMvc.perform(get("/rest/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_returnIsOkStatus_when_controllerCallsShowByIdMethod() throws Exception {

        String id = "16";

        this.mockMvc.perform(get("/rest/v1/students/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_returnCreatedStatus_when_controllerCallsCreateMethod() throws Exception {

        StudentDto studentDto = new StudentDto();
        studentDto.setId(5);
        studentDto.setFacultyId(1);
        studentDto.setFirstName("First");
        studentDto.setLastName("Last");

        this.mockMvc.perform(post("/rest/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(studentDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_returnIsNoContentStatus_when_controllerCallsUpdateMethod() throws Exception {

        StudentDto studentDto = new StudentDto();
        studentDto.setId(5);
        studentDto.setFacultyId(2);
        studentDto.setFirstName("First");
        studentDto.setLastName("Last");

        this.mockMvc.perform(put("/rest/v1/students/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(studentDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_returnIsNoContentStatus_when_controllerCallsDeleteMethod() throws Exception {

        StudentDto studentDto = new StudentDto();
        studentDto.setId(40);

        Mockito.when(serviceStudentMock.getByIdAsDto(40)).thenReturn(studentDto);

        this.mockMvc.perform(delete("/rest/v1/students/" + studentDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
