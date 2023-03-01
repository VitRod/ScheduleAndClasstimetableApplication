package ua.com.vit.controllers.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.vit.repository.entities.Faculty;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FacultyRestControllerIntegrationTest {

    @Autowired
    private FacultyRestController facultyRestController;

    @Test
    public void should_createNewFaculty_when_controllerCallsAddToDBMethod() {

        Faculty faculty = new Faculty();
        faculty.setId(4);
        faculty.setFacultyName("Faculty name");

        assertThat(facultyRestController.getAll(), not(hasItem(faculty)));
        facultyRestController.create(faculty);

        assertThat(facultyRestController.getAll(), hasItem(faculty));
//        assertEquals(facultyRestController.getById(faculty.getId()), faculty);

    }

    @Test
    public void should_updateFaculty_when_controllerCallsUpdateMethod() {

        Faculty faculty = facultyRestController.getById(1);
        faculty.setFacultyName("Changed faculty name");

        facultyRestController.update(faculty);

        assertEquals(facultyRestController.getById(1), faculty);

    }

    @Test
    public void should_deleteFaculty_when_controllerCallsDeleteMethod() {

        HttpServletRequest request = new MockHttpServletRequest();
        Faculty faculty = new Faculty();
        faculty.setId(4);
        faculty.setFacultyName("The best faculty");

        facultyRestController.create(faculty);
        assertThat(facultyRestController.getAll(), hasItem(faculty));

        facultyRestController.delete(4, request);
        assertThat(facultyRestController.getAll(), not(hasItem(faculty)));
    }

}
