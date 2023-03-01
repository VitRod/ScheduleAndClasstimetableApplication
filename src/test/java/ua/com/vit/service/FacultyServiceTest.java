package ua.com.vit.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ua.com.vit.repository.dao.FacultyRepository;
import ua.com.vit.repository.entities.Faculty;

@RunWith(MockitoJUnitRunner.class)
public class FacultyServiceTest {

    @InjectMocks
    private FacultyService facultyServiceMock;

    @Mock
    private FacultyRepository facultyRepositoryMock;

    @Test
    public void should_callGetAllMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        facultyServiceMock.getAll();

        Mockito.verify(facultyRepositoryMock).findAll();
    }

    @Test
    public void should_callGetByIdMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        int id = 1;

        facultyServiceMock.getById(id);

        Mockito.verify(facultyRepositoryMock).findById(id);
    }

    @Test
    public void should_callCreateMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        Faculty faculty = new Faculty();
        faculty.setFacultyName("Faculty of Power");

        facultyServiceMock.create(faculty);

        Mockito.verify(facultyRepositoryMock).saveAndFlush(faculty);
    }

    @Test
    public void should_callUpdateMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        Faculty faculty = new Faculty();
        faculty.setFacultyName("Faculty of Ring Power");

        facultyServiceMock.update(faculty);

        Mockito.verify(facultyRepositoryMock).saveAndFlush(faculty);
    }

    @Test
    public void should_callDeleteMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        Faculty faculty = new Faculty();
        faculty.setFacultyName("Faculty of the One Ring Power");

        facultyServiceMock.create(faculty);
        facultyServiceMock.delete(faculty);

        Mockito.verify(facultyRepositoryMock).delete(faculty);
    }
}
