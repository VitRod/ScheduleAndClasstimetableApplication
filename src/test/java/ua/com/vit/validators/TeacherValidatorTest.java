package ua.com.vit.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import ua.com.vit.exceptions.IndelibleEntityException;
import ua.com.vit.domain.dto.TeacherDto;
import ua.com.vit.service.TeacherService;

import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class TeacherValidatorTest {

    @Mock
    private TeacherValidator teacherValidatorMock;

    @Mock
    private TeacherService teacherServiceMock;

    @Test(expected = IndelibleEntityException.class)
    public void should_throwIndelibleEntityException_when_entityHasDeletionConstraints() {

        TeacherDto teacherDto = teacherServiceMock.getByIdAsDto(1);
        MockHttpServletRequest request = new MockHttpServletRequest();

        doThrow(IndelibleEntityException.class)
                .when(teacherValidatorMock)
                .checkForDeletion(teacherDto, request);

        teacherValidatorMock.checkForDeletion(teacherDto, request);
    }

    @Test
    public void should_doNothing_when_entityIsDeletable() {

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(8);
        MockHttpServletRequest request = new MockHttpServletRequest();

        teacherValidatorMock.checkForDeletion(teacherDto, request);
    }
}
