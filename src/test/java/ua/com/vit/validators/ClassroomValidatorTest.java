package ua.com.vit.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import ua.com.vit.exceptions.IndelibleEntityException;
import ua.com.vit.domain.dto.ClassroomDto;
import ua.com.vit.service.ClassroomService;

import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class ClassroomValidatorTest {

    @Mock
    private ClassroomValidator classroomValidatorMock;

    @Mock
    private ClassroomService classroomServiceMock;

    @Test(expected = IndelibleEntityException.class)
    public void should_throwIndelibleEntityException_when_entityHasDeletionConstraints() {

        ClassroomDto classroomDto = classroomServiceMock.getByIdAsDto(1);
        MockHttpServletRequest request = new MockHttpServletRequest();

        doThrow(IndelibleEntityException.class)
                .when(classroomValidatorMock)
                .checkForDeletion(classroomDto, request);

        classroomValidatorMock.checkForDeletion(classroomDto, request);
    }

    @Test
    public void should_doNothing_when_entityIsDeletable() {

        ClassroomDto classroomDto = new ClassroomDto();
        classroomDto.setId(52);
        MockHttpServletRequest request = new MockHttpServletRequest();

        classroomValidatorMock.checkForDeletion(classroomDto, request);
    }
}
