package ua.com.vit.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import ua.com.vit.exceptions.IndelibleEntityException;
import ua.com.vit.repository.entities.Course;
import ua.com.vit.service.CourseService;

import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class CourseValidatorTest {

    @Mock
    private CourseValidator courseValidatorMock;

    @Mock
    private CourseService courseServiceMock;

    @Test(expected = IndelibleEntityException.class)
    public void should_throwIndelibleEntityException_when_entityHasDeletionConstraints() {

        Course course = courseServiceMock.getById(1);
        MockHttpServletRequest request = new MockHttpServletRequest();

        doThrow(IndelibleEntityException.class)
                .when(courseValidatorMock)
                .checkForDeletion(course, request);

        courseValidatorMock.checkForDeletion(course, request);
    }

    @Test
    public void should_doNothing_when_entityIsDeletable() {

        Course course = new Course();
        course.setId(8);
        MockHttpServletRequest request = new MockHttpServletRequest();

        courseValidatorMock.checkForDeletion(course, request);
    }
}
