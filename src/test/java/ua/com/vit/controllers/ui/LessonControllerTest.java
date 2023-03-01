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
import ua.com.vit.domain.dto.LessonDto;
import ua.com.vit.service.ClassroomService;
import ua.com.vit.service.CourseService;
import ua.com.vit.service.LessonService;
import ua.com.vit.service.TeacherService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LessonControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private LessonService serviceLesson;

    @Autowired
    private ClassroomService serviceClassroom;

    @Autowired
    private CourseService serviceCourse;

    @Autowired
    private TeacherService serviceTeacher;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void should_getListOfLessons_when_controllerCallsShowAllMethod() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/lessons"))
                .andExpect(status().isOk())
                .andExpect(view().name("lessons/showAll"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(model().attribute("lessons", serviceLesson.getAllAsDto()))
                .andExpect(model().attribute("classrooms", serviceClassroom))
                .andExpect(model().attribute("courses", serviceCourse))
                .andExpect(model().attribute("teachers", serviceTeacher))
                .andReturn();
    }

    @Test
    public void should_getOneLesson_when_controllerCallsShowByIdMethod() throws Exception {

        int id = 157;

        this.mockMvc.perform(MockMvcRequestBuilders.get("/lessons/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("lessons/showById"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(model().attribute("lesson", serviceLesson.getByIdAsDto(id)))
                .andExpect(model().attribute("classrooms", serviceClassroom.getAllAsDto()))
                .andExpect(model().attribute("courses", serviceCourse.getAll()))
                .andExpect(model().attribute("teachers", serviceTeacher.getAllAsDto()))
                .andReturn();
    }

    @Test
    public void should_showCreationView_when_controllerCallsCreateMethod() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/lessons/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("lessons/create"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(model().attribute("classrooms", serviceClassroom.getAllAsDto()))
                .andExpect(model().attribute("courses", serviceCourse.getAll()))
                .andExpect(model().attribute("teachers", serviceTeacher.getAllAsDto()))
                .andReturn();
    }

    @Test
    public void should_addNewLessonToDatabase_when_controllerCallsAddToDBMethod() throws Exception {

        LessonDto lesson = new LessonDto();
        lesson.setDate(LocalDate.of(2021, 7, 1));
        lesson.setStartTime(LocalTime.of(9, 20));
        lesson.setEndTime(LocalTime.of(10, 0));
        lesson.setCourseId(1);
        lesson.setTeacherId(1);
        lesson.setClassroomId(4);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/lessons")
                        .param("date", convertDateToString(lesson.getDate()))
                        .param("startTime", convertTimeToString(lesson.getStartTime()))
                        .param("endTime", convertTimeToString(lesson.getEndTime()))
                        .param("classroomId", Integer.toString(lesson.getClassroomId()))
                        .param("courseId", Integer.toString(lesson.getCourseId()))
                        .param("teacherId", Integer.toString(lesson.getTeacherId())))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/lessons"))
                .andExpect(redirectedUrl("/lessons"))
                .andReturn();
    }

    @Test
    public void should_updateLessonInDatabase_when_controllerCallsUpdateMethod() throws Exception {

        LessonDto lesson = new LessonDto();
        lesson.setId(203);
        lesson.setDate(LocalDate.of(2021, 6, 15));
        lesson.setStartTime(LocalTime.of(14, 0));
        lesson.setEndTime(LocalTime.of(15, 40));
        lesson.setCourseId(3);
        lesson.setTeacherId(5);
        lesson.setClassroomId(15);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/lessons/{id}", lesson.getId())
                        .param("id", Integer.toString(lesson.getId()))
                        .param("date", convertDateToString(lesson.getDate()))
                        .param("startTime", convertTimeToString(lesson.getStartTime()))
                        .param("endTime", convertTimeToString(lesson.getEndTime()))
                        .param("classroomId", Integer.toString(lesson.getClassroomId()))
                        .param("courseId", Integer.toString(lesson.getCourseId()))
                        .param("teacherId", Integer.toString(lesson.getTeacherId())))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/lessons"))
                .andExpect(redirectedUrl("/lessons"))
                .andReturn();
    }

    @Test
    public void should_deleteLessonFromDatabase_when_controllerCallsDeleteMethodForDeletableEntity()
            throws Exception {

        LessonDto lesson = new LessonDto();
        lesson.setDate(LocalDate.of(2021, 8, 2));
        lesson.setStartTime(LocalTime.of(14, 0));
        lesson.setEndTime(LocalTime.of(15, 40));
        lesson.setCourseId(2);
        lesson.setTeacherId(2);
        lesson.setClassroomId(9);
        serviceLesson.createFromDto(lesson);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/lessons/{id}", lesson.getId())
                        .param("date", convertDateToString(lesson.getDate()))
                        .param("startTime", convertTimeToString(lesson.getStartTime()))
                        .param("endTime", convertTimeToString(lesson.getEndTime()))
                        .param("classroomId", Integer.toString(lesson.getClassroomId()))
                        .param("courseId", Integer.toString(lesson.getCourseId()))
                        .param("teacherId", Integer.toString(lesson.getTeacherId())))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/lessons"))
                .andExpect(redirectedUrl("/lessons"))
                .andReturn();
    }

    private String convertDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    private String convertTimeToString(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
        return time.format(formatter);
    }
}
