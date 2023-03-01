package ua.com.vit.controllers.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.vit.domain.dto.StudentDto;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentRestControllerIntegrationTest {

    @Autowired
    private StudentRestController studentRestController;

    @Test
    public void should_createNewStudent_when_controllerCallsAddToDBMethod() {

        Set<Integer> studentCourses = new HashSet<>();
        studentCourses.add(1);
        studentCourses.add(3);
        StudentDto student = new StudentDto();
        student.setId(62);
        student.setFirstName("Nick");
        student.setLastName("Jackson");
        student.setFacultyId(1);
        student.setCoursesId(studentCourses);

        assertThat(studentRestController.getAll(), not(hasItem(student)));

        studentRestController.create(student);

        assertThat(studentRestController.getAll(), hasItem(student));
        assertEquals(student, studentRestController.getById(student.getId()));

    }

    @Test
    public void should_updateStudent_when_controllerCallsUpdateMethod() {

        StudentDto student = studentRestController.getById(1);
        student.setLastName("Johnson");
        studentRestController.update(student);

        assertEquals(studentRestController.getById(1).getLastName(), "Johnson");
    }

    @Test
    public void should_deleteStudent_when_controllerCallsDeleteMethod() {

        Set<Integer> studentCourses = new HashSet<>();
        studentCourses.add(5);
        studentCourses.add(6);
        StudentDto student = new StudentDto();
        student.setId(61);
        student.setFirstName("Peter");
        student.setLastName("Berg");
        student.setFacultyId(3);
        student.setCoursesId(studentCourses);

        studentRestController.create(student);
//        assertThat(studentRestController.getAll(), hasItem(student));

//        studentRestController.delete(student.getId());
        assertThat(studentRestController.getAll(), not(hasItem(student)));
    }

}
