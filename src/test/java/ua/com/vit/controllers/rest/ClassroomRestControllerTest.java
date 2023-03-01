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
import ua.com.vit.domain.dto.ClassroomDto;
import ua.com.vit.exceptions.IndelibleEntityException;
import ua.com.vit.service.ClassroomService;
import ua.com.vit.validators.ClassroomValidator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ClassroomRestController.class)
public class ClassroomRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ClassroomService serviceClassroomMock;

    @MockBean
    private ClassroomValidator validatorClassroomMock;

    @Test
    public void should_returnIsOkStatus_when_controllerCallsShowAllMethod() throws Exception {

        this.mockMvc.perform(get("/rest/v1/classrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_returnIsOkStatus_when_controllerCallsShowByIdMethod() throws Exception {

        String id = "2";

        this.mockMvc.perform(get("/rest/v1/classrooms/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_returnCreatedStatus_when_controllerCallsCreateMethod() throws Exception {

        ClassroomDto classroomDto = new ClassroomDto();
        classroomDto.setBuildingId(1);
        classroomDto.setRoomName("A-11");
        classroomDto.setRoomType("Lecture");
        classroomDto.setRoomCapacity(25);

        this.mockMvc.perform(post("/rest/v1/classrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(classroomDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_returnIsNoContentStatus_when_controllerCallsUpdateMethod() throws Exception {

        ClassroomDto classroomDto = new ClassroomDto();
        classroomDto.setId(5);
        classroomDto.setBuildingId(2);
        classroomDto.setRoomName("B-11");
        classroomDto.setRoomType("Lecture");
        classroomDto.setRoomCapacity(25);

        this.mockMvc.perform(put("/rest/v1/classrooms/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(classroomDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_returnIsNoContentStatus_when_controllerCallsDeleteMethod() throws Exception {

        ClassroomDto classroomDto = new ClassroomDto();
        classroomDto.setId(5);
        classroomDto.setBuildingId(2);
        classroomDto.setRoomName("B-11");
        classroomDto.setRoomType("Lecture");
        classroomDto.setRoomCapacity(25);

        this.mockMvc.perform(delete("/rest/v1/classrooms/" + classroomDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_returnIsBadRequestStatus_when_controllerCallsDeleteMethodWithInvalidData()
            throws Exception {

        int classroomId = 1;

        Mockito.doThrow(new IndelibleEntityException()).when(serviceClassroomMock).deleteById(classroomId);

        this.mockMvc.perform(delete("/rest/v1/classrooms/" + classroomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
