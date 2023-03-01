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
import ua.com.vit.domain.dto.LessonDto;
import ua.com.vit.exceptions.ClassroomCapacityException;
import ua.com.vit.exceptions.InvalidLessonConditionsException;
import ua.com.vit.service.LessonService;
import ua.com.vit.service.StudentService;
import ua.com.vit.service.TeacherService;
import ua.com.vit.validators.LessonValidator;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LessonRestController.class)
public class LessonRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LessonService serviceLessonMock;

    @MockBean
    StudentService serviceStudent;

    @MockBean
    TeacherService serviceTeacher;

    @MockBean
    LessonValidator validatorLessonMock;

    @Test
    public void should_returnIsOkStatus_when_controllerCallsShowAllMethod() throws Exception {

        this.mockMvc.perform(get("/rest/v1/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_returnIsOkStatus_when_controllerCallsShowByIdMethod() throws Exception {

        String id = "5";

        this.mockMvc.perform(get("/rest/v1/lessons/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_returnCreatedStatus_when_controllerCallsCreateMethod() throws Exception {

        LessonDto lessonDto = new LessonDto();
        lessonDto.setDate(LocalDate.of(2021, 12, 7));
        lessonDto.setStartTime(LocalTime.of(9, 20));
        lessonDto.setEndTime(LocalTime.of(10, 0));
        lessonDto.setClassroomId(1);
        lessonDto.setCourseId(1);
        lessonDto.setTeacherId(1);

        this.mockMvc.perform(post("/rest/v1/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(lessonDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_returnBadRequestStatus_when_controllerCallsCreateMethodWithInvalidTeacher()
            throws Exception {

        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(720);
        lessonDto.setDate(LocalDate.of(2021, 2, 1));
        lessonDto.setStartTime(LocalTime.of(9, 20));
        lessonDto.setEndTime(LocalTime.of(10, 0));
        lessonDto.setClassroomId(1);
        lessonDto.setCourseId(1);
        lessonDto.setTeacherId(1);

        Mockito.doThrow(new InvalidLessonConditionsException()).when(serviceLessonMock).createFromDto(lessonDto);

        this.mockMvc.perform(post("/rest/v1/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(lessonDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_returnBadRequestStatus_when_controllerCallsCreateMethodWithInvalidTemporalConditions()
            throws Exception {

        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(512);
        lessonDto.setDate(LocalDate.of(2021, 2, 1));
        lessonDto.setStartTime(LocalTime.of(9, 20));
        lessonDto.setEndTime(LocalTime.of(10, 0));
        lessonDto.setClassroomId(1);
        lessonDto.setCourseId(1);
        lessonDto.setTeacherId(1);

        Mockito.doThrow(new InvalidLessonConditionsException()).when(serviceLessonMock).createFromDto(lessonDto);

        this.mockMvc.perform(post("/rest/v1/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(lessonDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_returnBadRequestStatus_when_controllerCallsCreateMethodWithInsufficientClassroom()
            throws Exception {

        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(707);
        lessonDto.setDate(LocalDate.of(2021, 2, 1));
        lessonDto.setStartTime(LocalTime.of(9, 20));
        lessonDto.setEndTime(LocalTime.of(10, 0));
        lessonDto.setClassroomId(5);
        lessonDto.setCourseId(1);
        lessonDto.setTeacherId(1);

        Mockito.doThrow(new ClassroomCapacityException()).when(serviceLessonMock).createFromDto(lessonDto);

        this.mockMvc.perform(post("/rest/v1/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(lessonDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_returnIsNoContentStatus_when_controllerCallsUpdateMethod() throws Exception {

        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(5);
        lessonDto.setDate(LocalDate.of(2021, 12, 7));
        lessonDto.setStartTime(LocalTime.of(11, 10));
        lessonDto.setEndTime(LocalTime.of(12, 45));
        lessonDto.setClassroomId(5);
        lessonDto.setCourseId(1);
        lessonDto.setTeacherId(1);

        Mockito.when(serviceLessonMock.getByIdAsDto(5)).thenReturn(lessonDto);

        this.mockMvc.perform(put("/rest/v1/lessons/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(lessonDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_returnIsNoContentStatus_when_controllerCallsDeleteMethod() throws Exception {

        int lessonId = 38;

        this.mockMvc.perform(delete("/rest/v1/lessons/" + lessonId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_returnIsOkStatus_when_controllerCallsGetScheduleWithTeacherEntity() throws Exception {

        Object person = "TeacherDto{id=1, firstName='Albert', lastName='Sykes', facultyId=3, coursesId=[1]}";
        LocalDate beginDate = LocalDate.of(2021, 3, 1);
        LocalDate endDate = LocalDate.of(2021, 3, 30);

        this.mockMvc.perform(get("/rest/v1/lessons/schedule")
                        .param("person", person.toString())
                        .param("beginDate", beginDate.toString())
                        .param("endDate", endDate.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_returnIsOkStatus_when_controllerCallsGetScheduleMethodWithStudentEntity() throws Exception {

        Object person = "StudentDto{id=1, firstName='Jacob', lastName='Rose', facultyId=1, coursesId=[6]}";
        LocalDate beginDate = LocalDate.of(2021, 4, 1);
        LocalDate endDate = LocalDate.of(2021, 4, 30);

        this.mockMvc.perform(get("/rest/v1/lessons/schedule")
                        .param("person", person.toString())
                        .param("beginDate", beginDate.toString())
                        .param("endDate", endDate.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

}
