package ua.com.vit.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.com.vit.exceptions.ClassroomCapacityException;
import ua.com.vit.exceptions.InvalidLessonConditionsException;
import ua.com.vit.domain.dto.LessonDto;
import ua.com.vit.service.LessonService;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class LessonValidatorTest {

    @Mock
    private LessonValidator lessonValidatorMock;

    @Mock
    private LessonService lessonServiceMock;

    @Test(expected = ClassroomCapacityException.class)
    public void should_throwClassroomCapacityException_when_classroomCapacityConditionsAreInappropriate() {

        LessonDto lessonDto = new LessonDto();
        lessonDto.setClassroomId(5);

        doThrow(ClassroomCapacityException.class)
                .when(lessonValidatorMock)
                .checkClassroomCapacity(lessonDto);

        lessonValidatorMock.checkClassroomCapacity(lessonDto);
    }

    @Test(expected = InvalidLessonConditionsException.class)
    public void should_throwInvalidLessonConditionsException_when_allConditionsAreInappropriate() {

        LessonDto lessonDto = new LessonDto();
        lessonDto.setDate(LocalDate.of(2021, 1, 20));
        lessonDto.setStartTime(LocalTime.of(9, 20));
        lessonDto.setEndTime(LocalTime.of(10, 0));


        doThrow(InvalidLessonConditionsException.class)
                .when(lessonValidatorMock)
                .checkConditions(lessonDto);

        lessonValidatorMock.checkConditions(lessonDto);
    }


    @Test
    public void should_doNothing_when_classroomCapacityConditionsAreAppropriate() {

        LessonDto lessonDto = new LessonDto();
        lessonDto.setClassroomId(1);

        lessonValidatorMock.checkClassroomCapacity(lessonDto);
    }

    @Test
    public void should_doNothing_when_allConditionsAreAppropriate() {

        LessonDto lessonDto = new LessonDto();
        lessonDto.setDate(LocalDate.of(2021, 9, 17));
        lessonDto.setStartTime(LocalTime.of(9, 20));
        lessonDto.setEndTime(LocalTime.of(10, 0));
        lessonDto.setClassroomId(1);
        lessonDto.setTeacherId(3);

        lessonValidatorMock.checkConditions(lessonDto);
    }
}
