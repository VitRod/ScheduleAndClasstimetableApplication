package ua.com.vit.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ua.com.vit.repository.dao.CourseRepository;
import ua.com.vit.repository.entities.Course;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceTest {

    @InjectMocks
    private CourseService courseServiceMock;

    @Mock
    private CourseRepository courseRepositoryMock;

    @Test
    public void should_callGetAllMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        courseServiceMock.getAll();

        Mockito.verify(courseRepositoryMock).findAll();
    }

    @Test
    public void should_callGetByIdMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        int id = 7;

        courseServiceMock.getById(id);

        Mockito.verify(courseRepositoryMock).findById(id);
    }

    @Test
    public void should_callCreateMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        Course course = new Course();
        course.setCourseName("Anatomy");

        courseServiceMock.create(course);

        Mockito.verify(courseRepositoryMock).saveAndFlush(course);
    }

    @Test
    public void should_callUpdateMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        Course course = new Course();
        course.setCourseName("Physiology");

        courseServiceMock.update(course);

        Mockito.verify(courseRepositoryMock).saveAndFlush(course);
    }

    @Test
    public void should_callDeleteMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        Course course = new Course();
        course.setCourseName("Heat engineering");

        courseServiceMock.create(course);
        courseServiceMock.delete(course);

        Mockito.verify(courseRepositoryMock).delete(course);
    }
}
