package ua.com.vit.controllers.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.vit.repository.entities.Course;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseRestControllerIntegrationTest {

    @Autowired
    private CourseRestController courseRestController;

    @Test
    public void should_createNewCourse_when_controllerCallsAddToDBMethod() {

        Course course = new Course();
        course.setId(11);
        course.setCourseName("Just a course");

        assertThat(courseRestController.getAll(), not(hasItem(course)));

        courseRestController.create(course);

        assertThat(courseRestController.getAll(), hasItem(course));
        assertEquals(courseRestController.getById(course.getId()), course);

    }

    @Test
    public void should_updateCourse_when_controllerCallsUpdateMethod() {

        Course course = courseRestController.getById(3);
        course.setCourseName("Name");

        courseRestController.update(course);

        assertEquals(courseRestController.getById(3).getCourseName(), "Name");

    }

    @Test
    public void should_deleteCourse_when_controllerCallsDeleteMethod() {

        HttpServletRequest request = new MockHttpServletRequest();
        Course course = new Course();
        course.setId(11);
        course.setCourseName("Course");

        courseRestController.create(course);
        assertThat(courseRestController.getAll(), hasItem(course));

        courseRestController.delete(course.getId(), request);
        assertThat(courseRestController.getAll(), not(hasItem(course)));

    }
}
