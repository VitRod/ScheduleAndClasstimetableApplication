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
import ua.com.vit.domain.dto.TeacherDto;
import ua.com.vit.service.CourseService;
import ua.com.vit.service.FacultyService;
import ua.com.vit.service.TeacherService;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TeacherControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TeacherService serviceTeacher;

    @Autowired
    private FacultyService serviceFaculty;

    @Autowired
    private CourseService serviceCourse;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void should_getListOfTeachers_when_controllerCallsShowAllMethod() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/teachers"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/showAll"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(model().attribute("teachers", serviceTeacher.getAllAsDto()))
                .andExpect(model().attribute("faculties", serviceFaculty))
                .andExpect(model().attribute("courses", serviceCourse))
                .andReturn();
    }

    @Test
    public void should_getOneTeacher_when_controllerCallsShowByIdMethod() throws Exception {

        int id = 9;

        this.mockMvc.perform(MockMvcRequestBuilders.get("/teachers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/showById"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(model().attribute("teacher", serviceTeacher.getByIdAsDto(id)))
                .andExpect(model().attribute("faculties", serviceFaculty.getAll()))
                .andExpect(model().attribute("allCourses", serviceCourse.getAll()))
                .andReturn();
    }

    @Test
    public void should_showCreationView_when_controllerCallsCreateMethod() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/teachers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers/create"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(model().attribute("faculties", serviceFaculty.getAll()))
                .andExpect(model().attribute("allCourses", serviceCourse.getAll()))
                .andReturn();
    }

    @Test
    public void should_addNewTeacherToDatabase_when_controllerCallsAddToDBMethod() throws Exception {

        Set<Integer> teacherCourses = new HashSet<>();
        teacherCourses.add(2);
        teacherCourses.add(8);

        TeacherDto teacher = new TeacherDto();
        teacher.setFirstName("Jeff");
        teacher.setLastName("Coff");
        teacher.setFacultyId(2);
        teacher.setCoursesId(teacherCourses);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/teachers")
                        .param("firstName", teacher.getFirstName())
                        .param("lastName", teacher.getLastName())
                        .param("facultyId", Integer.toString(teacher.getFacultyId()))
                        .param("coursesId", createStringOfValues(teacherCourses)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/teachers"))
                .andExpect(redirectedUrl("/teachers"))
                .andReturn();
    }

    @Test
    public void should_updateTeacherInDatabase_when_controllerCallsUpdateMethod() throws Exception {

        Set<Integer> teacherCourses = new HashSet<>();
        teacherCourses.add(1);
        teacherCourses.add(3);

        TeacherDto teacher = new TeacherDto();
        teacher.setId(3);
        teacher.setFirstName("Francesco");
        teacher.setLastName("Donni");
        teacher.setFacultyId(2);
        teacher.setCoursesId(teacherCourses);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/teachers/{id}", teacher.getId())
                        .param("id", Integer.toString(teacher.getId()))
                        .param("firstName", teacher.getFirstName())
                        .param("lastName", teacher.getLastName())
                        .param("facultyId", Integer.toString(teacher.getFacultyId()))
                        .param("coursesId", createStringOfValues(teacherCourses)))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/teachers"))
                .andExpect(redirectedUrl("/teachers"))
                .andReturn();
    }

//    @Test
//    public void should_deleteTeacherFromDatabase_when_controllerCallsDeleteMethodForDeletableEntity()
//            throws Exception {
//
//        Set<Integer> teacherCourses = new HashSet<>();
//
//        TeacherDto teacher = new TeacherDto();
//        teacher.setId(12);
//        teacher.setFirstName("Frank");
//        teacher.setLastName("Sinatra");
//        teacher.setFacultyId(3);
//        teacher.setCoursesId(teacherCourses);
//        serviceTeacher.createFromDto(teacher);
//
//        this.mockMvc.perform(MockMvcRequestBuilders.delete("/teachers/{id}", teacher.getId())
//                        .param("id", Integer.toString(teacher.getId()))
//                        .param("firstName", teacher.getFirstName())
//                        .param("lastName", teacher.getLastName())
//                        .param("facultyId", Integer.toString(teacher.getFacultyId()))
//                        .param("coursesId", createStringOfValues(teacher.getCoursesId())))
//                .andExpect(status().isFound())
//                .andExpect(view().name("redirect:/teachers"))
//                .andExpect(redirectedUrl("/teachers"))
//                .andReturn();
//    }

    private String createStringOfValues(Set<Integer> setOfValues) {
        StringBuilder stringBuilder = new StringBuilder();
        setOfValues.forEach(integer -> stringBuilder.append(integer).append(","));
        return stringBuilder.substring(0, stringBuilder.toString().length() - 1);
    }

}
