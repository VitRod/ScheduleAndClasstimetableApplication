package ua.com.vit.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import ua.com.vit.exceptions.IndelibleEntityException;
import ua.com.vit.repository.entities.Faculty;
import ua.com.vit.service.FacultyService;

import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class FacultyValidatorTest {

    @Mock
    private FacultyValidator facultyValidatorMock;

    @Mock
    private FacultyService facultyServiceMock;

    @Test(expected = IndelibleEntityException.class)
    public void should_throwIndelibleEntityException_when_entityHasDeletionConstraints() {

        Faculty faculty = facultyServiceMock.getById(1);
        MockHttpServletRequest request = new MockHttpServletRequest();

        doThrow(IndelibleEntityException.class)
                .when(facultyValidatorMock)
                .checkForDeletion(faculty, request);

        facultyValidatorMock.checkForDeletion(faculty, request);
    }

    @Test
    public void should_doNothing_when_entityIsDeletable() {

        Faculty faculty = new Faculty();
        faculty.setId(8);
        MockHttpServletRequest request = new MockHttpServletRequest();

        facultyValidatorMock.checkForDeletion(faculty, request);
    }

}
