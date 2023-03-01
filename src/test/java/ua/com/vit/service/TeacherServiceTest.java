package ua.com.vit.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ua.com.vit.domain.converters.LessonConverter;
import ua.com.vit.domain.converters.TeacherConverter;
import ua.com.vit.domain.dto.TeacherDto;
import ua.com.vit.repository.dao.LessonRepository;
import ua.com.vit.repository.dao.TeacherRepository;
import ua.com.vit.repository.entities.Faculty;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class TeacherServiceTest {

    @InjectMocks
    private TeacherService teacherServiceMock;

    @Mock
    private LessonRepository lessonRepositoryMock;

    @Mock
    private TeacherRepository teacherRepositoryMock;

    @Mock
    private TeacherConverter teacherConverterMock;

    @Mock
    private LessonConverter lessonConverterMock;

    @Test
    public void should_callReceiveLessonsOnDateRangeMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        int id = 3;
        LocalDate beginDate = LocalDate.of(2021, 03, 01);
        LocalDate endDate = LocalDate.of(2021, 03, 31);

        teacherServiceMock.receiveLessonsOnDateRange(id, beginDate, endDate)
                .stream()
                .map(lessonConverterMock::toEntity)
                .collect(Collectors.toList());

        Mockito.verify(lessonRepositoryMock).getLessonsForTeacherOnDateRange(id, beginDate, endDate);
    }

    @Test
    public void should_callGetAllMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        teacherServiceMock.getAllAsDto();

        Mockito.verify(teacherRepositoryMock).findAll();
    }

    @Test
    public void should_callGetByIdMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        int id = 7;

        teacherServiceMock.getByIdAsDto(id);

        Mockito.verify(teacherRepositoryMock).findById(id);
    }

    @Test
    public void should_callCreateMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        TeacherDto teacher = new TeacherDto();
        Faculty faculty = new Faculty();
        teacher.setFirstName("John");
        teacher.setLastName("Galt");
        teacher.setFacultyId(1);

        teacherServiceMock.createFromDto(teacher);

        Mockito.verify(teacherRepositoryMock).saveAndFlush(teacherConverterMock.toEntity(teacher));
    }

    @Test
    public void should_callUpdateMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        TeacherDto teacher = new TeacherDto();
        Faculty faculty = new Faculty();
        teacher.setFirstName("Joachim");
        teacher.setLastName("Belt");
        teacher.setFacultyId(2);

        teacherServiceMock.updateFromDto(teacher);

        Mockito.verify(teacherRepositoryMock).saveAndFlush(teacherConverterMock.toEntity(teacher));
    }

    @Test
    public void should_callDeleteByIdMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        TeacherDto teacher = new TeacherDto();
        Faculty faculty = new Faculty();
        teacher.setFirstName("Pet");
        teacher.setLastName("Peters");
        teacher.setFacultyId(3);

        teacherServiceMock.createFromDto(teacher);
        teacherServiceMock.deleteById(teacher.getId());

        Mockito.verify(teacherRepositoryMock).deleteById(teacher.getId());
    }

}
